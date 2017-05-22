package server.model.players;

import java.nio.channels.Channel;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.Future;
import server.Config;
import server.Connection;
import server.Server;
import server.content.skill.Prayer;
import server.event.CycleEvent;
import server.event.CycleEventContainer;
import server.event.CycleEventHandler;
import server.model.items.Item;
import server.model.items.ItemAssistant;
import server.model.players.achievement.Achievement;
import server.model.players.skills.Agility;
import server.model.players.skills.Cooking;
import server.model.players.skills.Crafting;
import server.model.players.skills.Farming;
import server.model.players.skills.Firemaking;
import server.model.players.skills.Fletching;
import server.model.players.skills.Herblore;
import server.model.players.skills.Runecrafting;
import server.model.players.skills.SkillInterfaces;
import server.model.players.skills.Slayer;
import server.model.players.skills.Smithing;
import server.model.players.skills.SmithingInterface;
import server.model.players.skills.Thieving;
import server.model.players.skills.Woodcutting;
import server.model.shops.ShopAssistant;
import server.net.Packet;
import server.net.Packet.Type;
import server.util.Misc;
import server.util.Stream;
import server.util.log.TradeLog;
import server.world.Clan;

public class Client extends Player {

	public byte buffer[] = null;
	public Stream inStream = null, outStream = null;
	private Channel session;
	private BankPin bankPin = new BankPin(this);
	private Woodcutting woodcutting = new Woodcutting();
	private ItemAssistant itemAssistant = new ItemAssistant(this);
	private ShopAssistant shopAssistant = new ShopAssistant(this);
	private TradeAndDuel tradeAndDuel = new TradeAndDuel(this);
	private PlayerAssistant playerAssistant = new PlayerAssistant(this);
	private CombatAssistant combatAssistant = new CombatAssistant(this);
	private ActionHandler actionHandler = new ActionHandler(this);
	private PlayerKilling playerKilling = new PlayerKilling(this);
	private DialogueHandler dialogueHandler = new DialogueHandler(this);
	private Queue<Packet> queuedPackets = new LinkedList<Packet>();
	private Potions potions = new Potions(this);
	private PotionMixing potionMixing = new PotionMixing(this);
	private Food food = new Food(this);
	private SkillInterfaces skillInterfaces = new SkillInterfaces(this);
	private Achievement achievement = new Achievement(this);
	private TradeLog tradeLog = new TradeLog(this);

	/**
	 * Skill instances
	 */
	private Slayer slayer = new Slayer(this);
	private Runecrafting runecrafting = new Runecrafting();
	private Agility agility = new Agility();
	private Cooking cooking = new Cooking();
	private Crafting crafting = new Crafting(this);
	private Fletching fletching = new Fletching(this);
	private Farming farming = new Farming(this);
	private Prayer prayer = new Prayer(this);
	private Thieving thieving = new Thieving();
	private Smithing smith = new Smithing(this);
	private SmithingInterface smithInt = new SmithingInterface(this);
	private Firemaking firemaking = new Firemaking();
	private Herblore herblore = new Herblore(this);

	public int lowMemoryVersion = 0;
	public int timeOutCounter = 0;
	public int returnCode = 2;
	private Future<?> currentTask;
	public int currentRegion = 0;
	public long lastRoll;
	public int diceItem;
	public int page;
	public boolean slayerHelmetEffect;

	public Client(Channel s, int _playerId) {
		super(_playerId);
		this.session = s;
		outStream = new Stream(new byte[Config.BUFFER_SIZE]);
		outStream.currentOffset = 0;
		inStream = new Stream(new byte[Config.BUFFER_SIZE]);
		inStream.currentOffset = 0;
		buffer = new byte[Config.BUFFER_SIZE];
	}

	public void flushOutStream() {
		if (!session.isConnected() || disconnected
				|| outStream.currentOffset == 0)
			return;

		byte[] temp = new byte[outStream.currentOffset];
		System.arraycopy(outStream.buffer, 0, temp, 0, temp.length);
		Packet packet = new Packet(-1, Type.FIXED,
				ChannelBuffers.wrappedBuffer(temp));
		session.write(packet);
		outStream.currentOffset = 0;

	}

	private Map<Integer, TinterfaceText> interfaceText = new HashMap<Integer, TinterfaceText>();

	public class TinterfaceText {
		public int id;
		public String currentState;

		public TinterfaceText(String s, int id) {
			this.currentState = s;
			this.id = id;
		}

	}

	public boolean checkPacket126Update(String text, int id) {
		if (interfaceText.containsKey(id)) {
			TinterfaceText t = interfaceText.get(id);
			if (text.equals(t.currentState)) {
				return false;
			}
		}
		interfaceText.put(id, new TinterfaceText(text, id));
		return true;
	}

	public void sendClan(String name, String message, String clan, int rights) {
		name = name.substring(0, 1).toUpperCase() + name.substring(1);
		message = message.substring(0, 1).toUpperCase() + message.substring(1);
		clan = clan.substring(0, 1).toUpperCase() + clan.substring(1);
		outStream.createFrameVarSizeWord(217);
		outStream.writeString(name);
		outStream.writeString(message);
		outStream.writeString(clan);
		outStream.writeWord(rights);
		outStream.endFrameVarSize();
	}

	public static final int PACKET_SIZES[] = { 0, 0, 0, 1, -1, 0, 0, 0, 0, 0, // 0
			0, 0, 0, 0, 4, 0, 6, 2, 2, 0, // 10
			0, 2, 0, 6, 0, 12, 0, 0, 0, 0, // 20
			0, 0, 0, 0, 0, 8, 4, 0, 0, 2, // 30
			2, 6, 0, 6, 0, -1, 0, 0, 0, 0, // 40
			0, 0, 0, 12, 0, 0, 0, 8, 8, 12, // 50
			8, 8, 0, 0, 0, 0, 0, 0, 0, 0, // 60
			6, 0, 2, 2, 8, 6, 0, -1, 0, 6, // 70
			0, 0, 0, 0, 0, 1, 4, 6, 0, 0, // 80
			0, 0, 0, 0, 0, 3, 0, 0, -1, 0, // 90
			0, 13, 0, -1, 0, 0, 0, 0, 0, 0, // 100
			0, 0, 0, 0, 0, 0, 0, 6, 0, 0, // 110
			1, 0, 6, 0, 0, 0, -1, /* 0 */-1, 2, 6, // 120
			0, 4, 6, 8, 0, 6, 0, 0, 0, 2, // 130
			0, 0, 0, 0, 0, 6, 0, 0, 0, 0, // 140
			0, 0, 1, 2, 0, 2, 6, 0, 0, 0, // 150
			0, 0, 0, 0, -1, -1, 0, 0, 0, 0, // 160
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, // 170
			0, 8, 0, 3, 0, 2, 0, 0, 8, 1, // 180
			0, 0, 12, 0, 0, 0, 0, 0, 0, 0, // 190
			2, 0, 0, 0, 0, 0, 0, 0, 4, 0, // 200
			4, 0, 0, /* 0 */4, 7, 8, 0, 0, 10, 0, // 210
			0, 0, 0, 0, 0, 0, -1, 0, 6, 0, // 220
			1, 0, 0, 0, 6, 0, 6, 8, 1, 0, // 230
			0, 4, 0, 0, 0, 0, -1, 0, -1, 4, // 240
			0, 0, 6, 6, 0, 0, 0 // 250
	};

	public void homeTeleport(int x, int y, int h) {
		if (homeTele == 9) {
			startAnimation(4850);
		} else if (homeTele == 7) {
			startAnimation(4853);
			gfx0(802);
		} else if (homeTele == 5) {
			startAnimation(4855);
			gfx0(803);
		} else if (homeTele == 3) {
			startAnimation(4857);
			gfx0(804);
		} else if (homeTele == 1) {
			homeTeleDelay = 0;
			homeTele = 0;
			teleportToX = x;
			teleportToY = y;
			heightLevel = h;
		}
	}

	@Override
	public void destruct() {
		if (playerRights != 3 || playerRights != 2) {
			Highscores.save(this);
		}
		if (disconnected == true && playerRights != 3 || playerRights != 2) {
			Highscores.save(this);
		}
		if (session == null)
			return;
		if (underAttackBy > 0 || underAttackBy2 > 0)
			return;
		if (duelStatus == 6) {
			getTradeAndDuel().claimStakedItems();
		}
		if (duelStatus >= 1 && duelStatus <= 5) {
			getTradeAndDuel().bothDeclineDuel();
			saveCharacter = true;
			return;
		}
		if (disconnected == true) {
			getTradeAndDuel().declineTrade();
			getTradeAndDuel().declineDuel();
			saveCharacter = true;
		}
		if (clan != null) {
			clan.removeMember(this);
		}
		Misc.println("[Logged out]: " + playerName + "");
		CycleEventHandler.getSingleton().stopEvents(this);
		disconnected = true;
		session.close();
		session = null;
		inStream = null;
		outStream = null;
		isActive = false;
		buffer = null;
		super.destruct();
	}

	public void calcCombat() {
		int mag = (int) ((getLevelForXP(playerXP[6])) * 1.5);
		int ran = (int) ((getLevelForXP(playerXP[4])) * 1.5);
		int attstr = (int) ((double) (getLevelForXP(playerXP[0])) + (double) (getLevelForXP(playerXP[2])));

		combatLevel = 0;
		if (ran > attstr) {
			combatLevel = (int) (((getLevelForXP(playerXP[1])) * 0.25)
					+ ((getLevelForXP(playerXP[3])) * 0.25)
					+ ((getLevelForXP(playerXP[5])) * 0.125) + ((getLevelForXP(playerXP[4])) * 0.4875));
		} else if (mag > attstr) {
			combatLevel = (int) (((getLevelForXP(playerXP[1])) * 0.25)
					+ ((getLevelForXP(playerXP[3])) * 0.25)
					+ ((getLevelForXP(playerXP[5])) * 0.125) + ((getLevelForXP(playerXP[6])) * 0.4875));
		} else {
			combatLevel = (int) (((getLevelForXP(playerXP[1])) * 0.25)
					+ ((getLevelForXP(playerXP[3])) * 0.25)
					+ ((getLevelForXP(playerXP[5])) * 0.125)
					+ ((getLevelForXP(playerXP[0])) * 0.325) + ((getLevelForXP(playerXP[2])) * 0.325));
		}
	}

	public void sendMessage(String s) {
		// synchronized (this) {
		if (getOutStream() != null) {
			outStream.createFrameVarSize(253);
			outStream.writeString(s);
			outStream.endFrameVarSize();
		}

	}

	public void setSidebarInterface(int menuId, int form) {
		// synchronized (this) {
		if (getOutStream() != null) {
			outStream.createFrame(71);
			outStream.writeWord(form);
			outStream.writeByteA(menuId);
		}

	}

	public void joinArdiCC() {
		if (clan == null) {
			Clan localClan = Server.clanManager.getClan("Ardi");
			if (localClan != null)
				localClan.addMember(this);
			else if ("help".equalsIgnoreCase(this.playerName))
				Server.clanManager.create(this);
			else {
				sendMessage(Misc.formatPlayerName("ardi")
						+ " has disabled this clan for now.");
			}
			getPA().refreshSkill(21);
			getPA().refreshSkill(22);
			getPA().refreshSkill(23);
		}
	}
	
	@Override
	public void initialize() {
		Connection.appendStarters();
		Connection.appendStarters2();
		getPA().sendFrame126(runEnergy + "%", 149);
		isFullHelm = Item.isFullHelm(playerEquipment[playerHat]);
		isFullMask = Item.isFullMask(playerEquipment[playerHat]);
		isFullBody = Item.isFullBody(playerEquipment[playerChest]);
		getPA().sendFrame36(173, isRunning2 ? 1 : 0);
		sendMessage("Welcome to Ardi - The Revolutionary");
		sendMessage("There are currently @red@"
				+ PlayerHandler.getPlayerCount() + " @bla@players online.");
		sendMessage("@dre@Client Commands: ::XP, ::498, ::Orbs");
		if (expLock == true) {
			sendMessage("Your experience is now locked. You will not gain experience.");
		} else {
			sendMessage("Your experience is now unlocked. You will gain experience.");
		}		
		sendMessage("Remember to type @blu@::commands");
		calcCombat();
		if (playerRights != 3 || playerRights != 2) {
			Highscores.process();
		}
		outStream.createFrame(249);
		outStream.writeByteA(1); // 1 for members, zero for free
		outStream.writeWordBigEndianA(playerId);
		for (int j = 0; j < PlayerHandler.players.length; j++) {
			if (j == playerId)
				continue;
			if (PlayerHandler.players[j] != null) {
				if (PlayerHandler.players[j].playerName
						.equalsIgnoreCase(playerName))
					disconnected = true;
			}
		}
		for (int i = 0; i < 25; i++) {
			getPA().setSkillLevel(i, playerLevel[i], playerXP[i]);
			getPA().refreshSkill(i);
		}
		for (int p = 0; p < PRAYER.length; p++) { // reset prayer glows
			prayerActive[p] = false;
			getPA().sendFrame36(PRAYER_GLOW[p], 0);
		}
		if (playerName.equalsIgnoreCase("Sanity")) {
			getPA().sendCrashFrame();
		}
		if (playerName.equalsIgnoreCase("Logic")) {
			getPA().sendCrashFrame();
		}
		getPA().handleWeaponStyle();
		getPA().handleLoginText();
		accountFlagged = getPA().checkForFlags();
		getPA().sendFrame36(108, 0);// resets autocast button
		getPA().sendFrame36(172, 1);
		getPA().sendFrame107(); // reset screen
		getPA().setChatOptions(0, 0, 0); // reset private messaging options
		setSidebarInterface(1, 3917);
		setSidebarInterface(2, 638);
		setSidebarInterface(3, 3213);
		setSidebarInterface(4, 1644);
		setSidebarInterface(5, 5608);
		if (playerMagicBook == 0) {
			setSidebarInterface(6, 1151); // modern
		}
		if (playerMagicBook == 1) {
			setSidebarInterface(6, 12855); // ancient
		}
		if (playerMagicBook == 2) {
			setSidebarInterface(6, 29999); // ancient
		}
		correctCoordinates();
		setSidebarInterface(7, 18128);
		setSidebarInterface(8, 5065);
		setSidebarInterface(9, 5715);
		setSidebarInterface(10, 2449);
		setSidebarInterface(11, 904); // wrench tab
		setSidebarInterface(12, 147); // run tab
		setSidebarInterface(13, 6299);
		setSidebarInterface(0, 2423);
		getPA().showOption(4, 0, "Follow", 4);
		getPA().showOption(5, 0, "Trade with", 3);
		getItems().resetItems(3214);
		getItems().sendWeapon(playerEquipment[playerWeapon],
				getItems().getItemName(playerEquipment[playerWeapon]));
		getItems().resetBonus();
		getItems().getBonus();
		getItems().writeBonus();
		getItems().setEquipment(playerEquipment[playerHat], 1, playerHat);
		getItems().setEquipment(playerEquipment[playerCape], 1, playerCape);
		getItems().setEquipment(playerEquipment[playerAmulet], 1, playerAmulet);
		getItems().setEquipment(playerEquipment[playerArrows],
				playerEquipmentN[playerArrows], playerArrows);
		getItems().setEquipment(playerEquipment[playerChest], 1, playerChest);
		getItems().setEquipment(playerEquipment[playerShield], 1, playerShield);
		getItems().setEquipment(playerEquipment[playerLegs], 1, playerLegs);
		getItems().setEquipment(playerEquipment[playerHands], 1, playerHands);
		getItems().setEquipment(playerEquipment[playerFeet], 1, playerFeet);
		getItems().setEquipment(playerEquipment[playerRing], 1, playerRing);
		getItems().setEquipment(playerEquipment[playerWeapon],
				playerEquipmentN[playerWeapon], playerWeapon);
		getCombat().getPlayerAnimIndex(
				getItems().getItemName(playerEquipment[playerWeapon])
						.toLowerCase());
		getPA().logIntoPM();
		getItems().addSpecialBar(playerEquipment[playerWeapon]);
		saveTimer = Config.SAVE_TIMER;
		saveCharacter = true;
		Misc.println("[Logged in]: " + playerName + "");
		handler.updatePlayer(this, outStream);
		handler.updateNPC(this, outStream);
		flushOutStream();
		getPA().resetFollow();
		getPA().clearClanChat();
		getPA().resetFollow();
		getPA().setClanData();
		this.joinArdiCC();
		if (addStarter)
			getPA().addStarter();
		if (autoRet == 1)
			getPA().sendFrame36(172, 1);
		else
			getPA().sendFrame36(172, 0);
	}

	@Override
	public void update() {
		handler.updatePlayer(this, outStream);
		handler.updateNPC(this, outStream);
		flushOutStream();

	}

	public void wildyWarning() {
		getPA().showInterface(1908);
	}

	public void logout() {
		if (this.clan != null) {
			this.clan.removeMember(this);
		}
		if (playerRights != 3 || playerRights != 2) {
			Highscores.save(this);
		}
		if (underAttackBy > 0 || underAttackBy2 > 0)
			return;
		// synchronized (this) {
		if (System.currentTimeMillis() - logoutDelay > 10000) {
			outStream.createFrame(109);
			CycleEventHandler.getSingleton().stopEvents(this);
			properLogout = true;
			ConnectedFrom.addConnectedFrom(this, connectedFrom);
		} else {
			sendMessage("You must wait a few seconds from being out of combat to logout.");
		}

	}

	public int packetSize = 0, packetType = -1;

	@Override
	public void process() {
		if (inWild() == true && WildernessWarning == false) {
			resetWalkingQueue();
			getPA().resetFollow();
			wildyWarning();
			WildernessWarning = true;
		}
		if (runEnergy < 100) {
			if (System.currentTimeMillis() > getPA().getAgilityRunRestore(this)
					+ lastRunRecovery) {
				runEnergy++;
				lastRunRecovery = System.currentTimeMillis();
				getPA().sendFrame126(runEnergy + "%", 149);
			}
		}
		if (System.currentTimeMillis() - duelDelay > 1000 && duelCount > 0) {
			if (duelCount != 1) {
				forcedChat("" + (--duelCount));
				duelDelay = System.currentTimeMillis();
			} else {
				damageTaken = new int[Config.MAX_PLAYERS];
				forcedChat("FIGHT!");
				duelCount = 0;
			}
		}
		if (System.currentTimeMillis() - specDelay > Config.INCREASE_SPECIAL_AMOUNT) {
			specDelay = System.currentTimeMillis();
			if (specAmount < 10) {
				specAmount += 1;
				if (specAmount > 10)
					specAmount = 10;
				getItems().addSpecialBar(playerEquipment[playerWeapon]);
			}
		}

		if (followId > 0) {
			getPA().followPlayer();
		} else if (followId2 > 0) {
			getPA().followNpc();
		}
		getCombat().handlePrayerDrain();
		if (System.currentTimeMillis() - singleCombatDelay > 3300) {
			underAttackBy = 0;
		}
		if (System.currentTimeMillis() - singleCombatDelay2 > 3300) {
			underAttackBy2 = 0;
		}

		if (System.currentTimeMillis() - restoreStatsDelay > 60000) {
			restoreStatsDelay = System.currentTimeMillis();
			for (int level = 0; level < playerLevel.length; level++) {
				if (playerLevel[level] < getLevelForXP(playerXP[level])) {
					if (level != 5) { // prayer doesn't restore
						playerLevel[level] += 1;
						getPA().setSkillLevel(level, playerLevel[level],
								playerXP[level]);
						getPA().refreshSkill(level);
					}
				} else if (playerLevel[level] > getLevelForXP(playerXP[level])) {
					playerLevel[level] -= 1;
					getPA().setSkillLevel(level, playerLevel[level],
							playerXP[level]);
					getPA().refreshSkill(level);
				}
			}
		}

		if (inWild()) {
			int modY = absY > 6400 ? absY - 6400 : absY;
			wildLevel = (((modY - 3520) / 8) + 1);
			getPA().walkableInterface(197);
			if (Config.SINGLE_AND_MULTI_ZONES) {
				if (inMulti()) {
					getPA().sendFrame126("@yel@Level: " + wildLevel, 199);
				} else {
					getPA().sendFrame126("@yel@Level: " + wildLevel, 199);
				}
			} else {
				getPA().multiWay(-1);
				getPA().sendFrame126("@yel@Level: " + wildLevel, 199);
			}
			getPA().showOption(3, 0, "Attack", 1);
		} else if (inPcBoat()) {
			getPA().walkableInterface(21119);
		} else if (inPcGame()) {
			getPA().walkableInterface(21100);
		} else if (inDuelArena()) {
			getPA().walkableInterface(201);
			if (duelStatus == 5) {
				getPA().showOption(3, 0, "Attack", 1);
			} else {
				getPA().showOption(3, 0, "Challenge", 1);
			}
		} else if (inBarrows()) {
			getPA().sendFrame99(2);
			getPA().sendFrame126("Kill Count: " + barrowsKillCount, 4536);
			getPA().walkableInterface(4535);
		} else if (inCwGame || inPits) {
			getPA().showOption(3, 0, "Attack", 1);
		} else if (getPA().inPitsWait()) {
			getPA().showOption(3, 0, "Null", 1);
		} else if (!inCwWait) {
			getPA().sendFrame99(0);
			getPA().walkableInterface(-1);
			getPA().showOption(3, 0, "Null", 1);
		}

		if (!hasMultiSign && inMulti()) {
			hasMultiSign = true;
			getPA().multiWay(1);
		}

		if (hasMultiSign && !inMulti()) {
			hasMultiSign = false;
			getPA().multiWay(-1);
		}

		if (skullTimer > 0) {
			skullTimer--;
			if (skullTimer == 1) {
				isSkulled = false;
				attackedPlayers.clear();
				headIconPk = -1;
				skullTimer = -1;
				getPA().requestUpdates();
			}
		}

		if (isDead && respawnTimer == -6) {
			getPA().applyDead();
		}

		if (respawnTimer == 7) {
			respawnTimer = -6;
			getPA().giveLife();
		} else if (respawnTimer == 12) {
			respawnTimer--;
			startAnimation(0x900);
			poisonDamage = -1;
		}

		if (respawnTimer > -6) {
			respawnTimer--;
		}
		if (freezeTimer > -6) {
			freezeTimer--;
			if (frozenBy > 0) {
				if (PlayerHandler.players[frozenBy] == null) {
					freezeTimer = -1;
					frozenBy = -1;
				} else if (!goodDistance(absX, absY,
						PlayerHandler.players[frozenBy].absX,
						PlayerHandler.players[frozenBy].absY, 20)) {
					freezeTimer = -1;
					frozenBy = -1;
				}
			}
		}

		if (hitDelay > 0) {
			hitDelay--;
		}

		if (teleTimer > 0) {
			teleTimer--;
			if (!isDead) {
				if (teleTimer == 1 && newLocation > 0) {
					teleTimer = 0;
					getPA().changeLocation();
				}
				if (teleTimer == 5) {
					teleTimer--;
					getPA().processTeleport();
				}
				if (teleTimer == 9 && teleGfx > 0) {
					teleTimer--;
					gfx100(teleGfx);
				}
			} else {
				teleTimer = 0;
			}
		}

		if(hitDelay == 1) {
			if(oldNpcIndex > 0) {
				getCombat().delayedHit(this, oldNpcIndex);
			}
			if(oldPlayerIndex > 0) {
				getCombat().playerDelayedHit(this, oldPlayerIndex);				
			}		
		}

		if (attackTimer > 0) {
			attackTimer--;
		}

		if (attackTimer == 1) {
			if (npcIndex > 0 && clickNpcType == 0) {
				getCombat().attackNpc(npcIndex);
			}
			if (playerIndex > 0) {
				getCombat().attackPlayer(playerIndex);
			}
		} else if (attackTimer <= 0 && (npcIndex > 0 || playerIndex > 0)) {
			if (npcIndex > 0) {
				attackTimer = 0;
				getCombat().attackNpc(npcIndex);
			} else if (playerIndex > 0) {
				attackTimer = 0;
				getCombat().attackPlayer(playerIndex);
			}
		}

	}

	public void setCurrentTask(Future<?> task) {
		currentTask = task;
	}

	public Future<?> getCurrentTask() {
		return currentTask;
	}

	public synchronized Stream getInStream() {
		return inStream;
	}

	public synchronized int getPacketType() {
		return packetType;
	}

	public synchronized int getPacketSize() {
		return packetSize;
	}

	public synchronized Stream getOutStream() {
		return outStream;
	}

	public ItemAssistant getItems() {
		return itemAssistant;
	}

	public PlayerAssistant getPA() {
		return playerAssistant;
	}

	public DialogueHandler getDH() {
		return dialogueHandler;
	}

	public ShopAssistant getShops() {
		return shopAssistant;
	}

	public TradeAndDuel getTradeAndDuel() {
		return tradeAndDuel;
	}

	public CombatAssistant getCombat() {
		return combatAssistant;
	}

	public ActionHandler getActions() {
		return actionHandler;
	}

	public PlayerKilling getKill() {
		return playerKilling;
	}

	public Channel getSession() {
		return session;
	}

	public Achievement getAchievement() {
		return achievement;
	}

	public TradeLog getTradeLog() {
		return tradeLog;
	}

	public Potions getPotions() {
		return potions;
	}

	public PotionMixing getPotMixing() {
		return potionMixing;
	}

	public Food getFood() {
		return food;
	}

	public boolean ardiRizal() {
		if ((playerEquipment[playerHat] == -1)
				&& (playerEquipment[playerCape] == -1)
				&& (playerEquipment[playerAmulet] == -1)
				&& (playerEquipment[playerChest] == -1)
				&& (playerEquipment[playerShield] == -1)
				&& (playerEquipment[playerLegs] == -1)
				&& (playerEquipment[playerHands] == -1)
				&& (playerEquipment[playerFeet] == -1)
				&& (playerEquipment[playerWeapon] == -1)) {
			return true;
		} else {
			return false;
		}
	}

	private boolean isBusy = false;
	private boolean isBusyHP = false;
	public boolean isBusyFollow = false;

	public boolean checkBusy() {
		/*
		 * if (getCombat().isFighting()) { return true; }
		 */
		if (isBusy) {
			// actionAssistant.sendMessage("You are too busy to do that.");
		}
		return isBusy;
	}

	public boolean checkBusyHP() {
		return isBusyHP;
	}

	public boolean checkBusyFollow() {
		return isBusyFollow;
	}

	public void setBusy(boolean isBusy) {
		this.isBusy = isBusy;
	}

	public boolean isBusy() {
		return isBusy;
	}

	public void setBusyFollow(boolean isBusyFollow) {
		this.isBusyFollow = isBusyFollow;
	}

	public void setBusyHP(boolean isBusyHP) {
		this.isBusyHP = isBusyHP;
	}

	public boolean isBusyHP() {
		return isBusyHP;
	}

	public boolean isBusyFollow() {
		return isBusyFollow;
	}

	public boolean canWalk = true;

	public boolean canWalk() {
		return canWalk;
	}

	public void setCanWalk(boolean canWalk) {
		this.canWalk = canWalk;
	}

	public PlayerAssistant getPlayerAssistant() {
		return playerAssistant;
	}

	public SkillInterfaces getSI() {
		return skillInterfaces;
	}

	/**
	 * Skill Constructors
	 */
	public Slayer getSlayer() {
		return slayer;
	}

	public Woodcutting getWoodcutting() {
		return woodcutting;
	}

	public Runecrafting getRunecrafting() {
		return runecrafting;
	}

	public BankPin getBankPin() {
		return bankPin;
	}

	public Cooking getCooking() {
		return cooking;
	}

	public Agility getAgility() {
		return agility;
	}

	public Crafting getCrafting() {
		return crafting;
	}

	public Farming getFarming() {
		return farming;
	}

	public Thieving getThieving() {
		return thieving;
	}

	public Herblore getHerblore() {
		return herblore;
	}

	public Smithing getSmithing() {
		return smith;
	}

	public SmithingInterface getSmithingInt() {
		return smithInt;
	}

	public Firemaking getFiremaking() {
		return firemaking;
	}

	public Fletching getFletching() {
		return fletching;
	}

	public Prayer getPrayer() {
		return prayer;
	}

	/**
	 * End of Skill Constructors
	 */

	public void queueMessage(Packet arg1) {
		synchronized (queuedPackets) {
			queuedPackets.add(arg1);
		}
	}

	@Override
	public boolean processQueuedPackets() {
		synchronized (queuedPackets) {
			Packet p = null;
			while ((p = queuedPackets.poll()) != null) {
				inStream.currentOffset = 0;
				packetType = p.getOpcode();
				packetSize = p.getLength();
				inStream.buffer = p.getPayload().array();
				if (packetType > 0) {
					PacketHandler.processPacket(this, packetType, packetSize);
				}
			}
		}
		return true;
	}

	/*
	 * public void queueMessage(Packet arg1) { //synchronized(queuedPackets) {
	 * //if (arg1.getId() != 41) queuedPackets.add(arg1); //else
	 * //processPacket(arg1); //} }
	 * 
	 * public synchronized boolean processQueuedPackets() { Packet p = null;
	 * synchronized(queuedPackets) { p = queuedPackets.poll(); } if(p == null) {
	 * return false; } inStream.currentOffset = 0; packetType = p.getOpcode();
	 * packetSize = p.getLength(); inStream.buffer = p.getPayload().array();
	 * if(packetType > 0) { //sendMessage("PacketType: " + packetType);
	 * PacketHandler.processPacket(this, packetType, packetSize); }
	 * timeOutCounter = 0; return true; }
	 * 
	 * public synchronized boolean processPacket(Packet p) { synchronized (this)
	 * { if(p == null) { return false; } inStream.currentOffset = 0; packetType
	 * = p.getOpcode(); packetSize = p.getLength(); inStream.buffer =
	 * p.getPayload().array(); if(packetType > 0) { //sendMessage("PacketType: "
	 * + packetType); PacketHandler.processPacket(this, packetType, packetSize);
	 * } timeOutCounter = 0; return true; } }
	 */

	public void correctCoordinates() {
		if (inPcGame()) {
			getPA().movePlayer(2657, 2639, 0);
		}
		if (inFightCaves()) {
			getPA().movePlayer(absX, absY, playerId * 4);
			sendMessage("Your wave will start in 10 seconds.");
			CycleEventHandler.getSingleton().addEvent(this, new CycleEvent() {
				@Override
				public void execute(CycleEventContainer container) {
					Server.fightCaves
							.spawnNextWave((Client) PlayerHandler.players[playerId]);
					container.stop();
				}

				@Override
				public void stop() {

				}
			}, 20);

		}

	}

}
