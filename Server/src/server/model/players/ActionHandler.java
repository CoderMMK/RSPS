package server.model.players;

import server.Server;
import server.model.minigames.Sailing;
import server.model.objects.Object;
import server.model.players.skills.Fishing;
import server.model.players.skills.Mining;
import server.model.players.skills.Runecrafting;
import server.model.players.skills.Thieving;
import server.model.players.skills.Woodcutting;
import server.util.Misc;
import server.util.ScriptManager;

public class ActionHandler {

	private Client c;

	public ActionHandler(Client Client) {
		this.c = Client;
	}

	public void firstClickObject(int objectType, int obX, int obY) {
		c.clickObjectType = 0;
		if (Woodcutting.playerTrees(c, objectType)) {
			Woodcutting.attemptData(c, objectType, obX, obY);
			return;
		}
		if (Mining.miningRocks(c, objectType)) {
			Mining.attemptData(c, objectType, obX, obY);
			return;
		}
		switch (objectType) {
		case 1733:
			c.getPA().movePlayer(c.absX, c.absY + 6393, 0);
			break;

		case 1734:
			c.getPA().movePlayer(c.absX, c.absY - 6396, 0);
			break;
		case 14314:
			this.c.getPA().movePlayer(2657, 2639, 0);
			break;
		case 14315:
			this.c.getPA().movePlayer(2661, 2639, 0);
			break;
		case 245:
			this.c.getPA().movePlayer(this.c.absX, this.c.absY + 2, 2);
			break;
		case 246:
			this.c.getPA().movePlayer(this.c.absX, this.c.absY - 2, 1);
			break;
		case 272:
			this.c.getPA().movePlayer(this.c.absX, this.c.absY, 1);
			break;
		case 273:
			this.c.getPA().movePlayer(this.c.absX, this.c.absY, 0);
			break;
		/* Godwars Door */
		case 26426: // armadyl
			if (c.absX == 2839 && c.absY == 5295) {
				c.getPA().movePlayer(2839, 5296, 2);
				c.sendMessage("@blu@Ardi wishes the best of luck to you");
			} else {
				c.getPA().movePlayer(2839, 5295, 2);
			}
			break;
		case 26425: // bandos
			if (c.absX == 2863 && c.absY == 5354) {
				c.getPA().movePlayer(2864, 5354, 2);
				c.sendMessage("@blu@Ardi wishes the best of luck to you");
			} else {
				c.getPA().movePlayer(2863, 5354, 2);
			}
			break;
		case 26428: // bandos
			if (c.absX == 2925 && c.absY == 5332) {
				c.getPA().movePlayer(2925, 5331, 2);
				c.sendMessage("@blu@Ardi wishes the best of luck to you");
			} else {
				c.getPA().movePlayer(2925, 5332, 2);
			}
			break;
		case 26427: // bandos
			if (c.absX == 2908 && c.absY == 5265) {
				c.getPA().movePlayer(2907, 5265, 0);
				c.sendMessage("@blu@Ardi wishes the best of luck to you");
			} else {
				c.getPA().movePlayer(2908, 5265, 0);
			}
			break;

		case 5960:
			if (c.leverClicked == false) {
				c.getDH().sendDialogues(114, 9985);
				c.leverClicked = true;
			} else {
				c.getPA().startLeverTeleport(3090, 3956, 0, "lever");
			}
			break;
		case 3223:
		case 21764:
		case 411:
		case 26289:
			if (c.playerLevel[5] < c.getPA().getLevelForXP(c.playerXP[5])) {
				c.playerLevel[5] = c.getPA().getLevelForXP(c.playerXP[5]);
				// c.sendMessage("You recharge your prayer points.");
				c.getPA().refreshSkill(5);
			}
			c.playerLevel[3] = c.getPA().getLevelForXP(c.playerXP[3]);
			c.getPA().refreshSkill(3);
			c.startAnimation(645);
			c.specAmount = 10.0;
			c.getItems().addSpecialBar(c.playerEquipment[c.playerWeapon]);
			c.sendMessage("@red@You've recharged your special attack, prayer points and hitpoints.");
			break;
		case 5959:
			c.getPA().startLeverTeleport(2539, 4712, 0, "lever");
			break;
		case 1814:
			this.c.getPA().startLeverTeleport(3153, 3923, 0, "lever");
			break;
		case 1815:
			this.c.getPA().startLeverTeleport(3087, 3500, 0, "lever");
			break;
		/* Start Brimhavem Dungeon */
		case 2879:
			c.getPA().movePlayer(2542, 4718, 0);
			break;
		case 2878:
			c.getPA().movePlayer(2509, 4689, 0);
			break;
		case 5083:
			c.getPA().movePlayer(2713, 9564, 0);
			c.sendMessage("You enter the dungeon.");
			break;

		case 5103:
			if (c.absX == 2691 && c.absY == 9564) {
				c.getPA().movePlayer(2689, 9564, 0);
			} else if (c.absX == 2689 && c.absY == 9564) {
				c.getPA().movePlayer(2691, 9564, 0);
			}
			break;

		case 5106:
			if (c.absX == 2674 && c.absY == 9479) {
				c.getPA().movePlayer(2676, 9479, 0);
			} else if (c.absX == 2676 && c.absY == 9479) {
				c.getPA().movePlayer(2674, 9479, 0);
			}
			break;

		case 5105:
			if (c.absX == 2672 && c.absY == 9499) {
				c.getPA().movePlayer(2674, 9499, 0);
			} else if (c.absX == 2674 && c.absY == 9499) {
				c.getPA().movePlayer(2672, 9499, 0);
			}
			break;

		case 5107:
			if (c.absX == 2693 && c.absY == 9482) {
				c.getPA().movePlayer(2695, 9482, 0);
			} else if (c.absX == 2695 && c.absY == 9482) {
				c.getPA().movePlayer(2693, 9482, 0);
			}
			break;

		case 5104:
			if (c.absX == 2683 && c.absY == 9568) {
				c.getPA().movePlayer(2683, 9570, 0);
			} else if (c.absX == 2683 && c.absY == 9570) {
				c.getPA().movePlayer(2683, 9568, 0);
			}
			break;

		case 5100:
			if (c.absY <= 9567) {
				c.getPA().movePlayer(2655, 9573, 0);
			} else if (c.absY >= 9572) {
				c.getPA().movePlayer(2655, 9566, 0);
			}
			break;

		case 5099:
			if (c.absY <= 9493) {
				c.getPA().movePlayer(2698, 9500, 0);
			} else if (c.absY >= 9499) {
				c.getPA().movePlayer(2698, 9492, 0);
			}
			break;

		case 5088:
			if (c.absX <= 2682) {
				c.getPA().movePlayer(2687, 9506, 0);
			} else if (c.absX >= 2687) {
				c.getPA().movePlayer(2682, 9506, 0);
			}
			break;

		case 5110:
			c.getPA().movePlayer(2647, 9557, 0);
			c.sendMessage("You cross the lava");
			break;

		case 5111:
			c.getPA().movePlayer(2649, 9562, 0);
			c.sendMessage("You cross the lava");
			break;

		case 5084:
			c.getPA().movePlayer(2744, 3151, 0);
			c.sendMessage("You exit the dungeon.");
			break;
		/* End Brimhavem Dungeon */
		case 6481:
			c.getPA().movePlayer(3233, 9315, 0);
			break;
		case 17010:
			if (c.playerMagicBook == 0) {
				c.sendMessage("You switch spellbook to lunar magic.");
				c.setSidebarInterface(6, 29999);
				c.playerMagicBook = 2;
				c.autocasting = false;
				c.autocastId = -1;
				c.getPA().resetAutocast();
				break;
			}
			if (c.playerMagicBook == 1) {
				c.sendMessage("You switch spellbook to lunar magic.");
				c.setSidebarInterface(6, 29999);
				c.playerMagicBook = 2;
				c.autocasting = false;
				c.autocastId = -1;
				c.getPA().resetAutocast();
				break;
			}
			if (c.playerMagicBook == 2) {
				c.setSidebarInterface(6, 1151);
				c.playerMagicBook = 0;
				c.autocasting = false;
				c.sendMessage("You feel a drain on your memory.");
				c.autocastId = -1;
				c.getPA().resetAutocast();
				break;
			}
			break;

		case 1551:
			if (c.absX == 3252 && c.absY == 3266) {
				c.getPA().movePlayer(3253, 3266, 0);
			}
			if (c.absX == 3253 && c.absY == 3266) {
				c.getPA().movePlayer(3252, 3266, 0);
			}
			break;
		case 1553:
			if (c.absX == 3252 && c.absY == 3267) {
				c.getPA().movePlayer(3253, 3266, 0);
			}
			if (c.absX == 3253 && c.absY == 3267) {
				c.getPA().movePlayer(3252, 3266, 0);
			}
			break;

		case 2491:
			Mining.mineEss(c, objectType);
			break;
		case 3044:
			c.getSmithing().sendSmelting();
			break;
		// abyss rifts
		case 7129: // fire riff
			Runecrafting.craftRunesOnAltar(c, 14, 7, 554, 50, 60, 70);
			break;
		case 7130: // earth riff
			Runecrafting.craftRunesOnAltar(c, 9, 7, 557, 45, 55, 65);
			break;
		case 7131: // body riff
			Runecrafting.craftRunesOnAltar(c, 20, 8, 559, 55, 65, 75);
			break;
		case 7132: // cosmic riff
			Runecrafting.craftRunesOnAltar(c, 35, 10, 564, 72, 84, 96);
			break;
		case 7133: // nat riff
			Runecrafting.craftRunesOnAltar(c, 44, 9, 561, 60, 74, 91);
			break;
		case 7134: // chaos riff
			Runecrafting.craftRunesOnAltar(c, 35, 9, 562, 60, 70, 80);
			break;
		case 7135: // law riff
			Runecrafting.craftRunesOnAltar(c, 54, 10, 563, 65, 79, 93);
			break;
		case 7136: // death riff
			Runecrafting.craftRunesOnAltar(c, 65, 10, 560, 72, 84, 96);
			break;
		case 7137: // water riff
			Runecrafting.craftRunesOnAltar(c, 5, 6, 555, 30, 45, 60);
			break;
		case 7138: // soul riff
			Runecrafting.craftRunesOnAltar(c, 65, 10, 566, 72, 84, 96);
			break;
		case 7139: // air riff
			Runecrafting.craftRunesOnAltar(c, 1, 5, 556, 30, 45, 60);
			break;
		case 7140: // mind riff
			Runecrafting.craftRunesOnAltar(c, 1, 5, 558, 30, 45, 60);
			break;
		case 7141: // blood rift
			Runecrafting.craftRunesOnAltar(c, 77, 11, 565, 89, 94, 99);
			break;

		/* AL KHARID */
		case 2883:
		case 2882:
			c.getDH().sendDialogues(1023, 925);
			break;
		case 2412:
			Sailing.startTravel(c, 1);
			break;
		case 2414:
			Sailing.startTravel(c, 2);
			break;
		case 2083:
			Sailing.startTravel(c, 5);
			break;
		case 2081:
			Sailing.startTravel(c, 6);
			break;
		case 14304:
			Sailing.startTravel(c, 14);
			break;
		case 14306:
			Sailing.startTravel(c, 15);
			break;

		case 2213:
		case 3045:
		case 14367:
		case 11758:
		case 3193:
		case 10517:
		case 11402:
		case 26972:
		case 4483:
			c.getPA().openUpBank();
			break;

		/**
		 * Entering the Fight Caves.
		 */
		case 9356:
			if (c.waveId > 0) {
				return;
			}
			c.getPA().enterCaves();
			break;

		/**
		 * Clicking on the Ancient Altar.
		 */
		case 6552:
			if (c.playerMagicBook == 0) {
				c.playerMagicBook = 1;
				c.setSidebarInterface(6, 12855);
				c.autocasting = false;
				c.sendMessage("An ancient wisdomin fills your mind.");
				c.getPA().resetAutocast();
			} else {
				c.setSidebarInterface(6, 1151);
				c.playerMagicBook = 0;
				c.autocasting = false;
				c.sendMessage("You feel a drain on your memory.");
				c.autocastId = -1;
				c.getPA().resetAutocast();
			}
			break;

		/**
		 * Recharing prayer points.
		 */
		case 409:
			if (c.playerLevel[5] < c.getPA().getLevelForXP(c.playerXP[5])) {
				c.startAnimation(645);
				c.playerLevel[5] = c.getPA().getLevelForXP(c.playerXP[5]);
				c.sendMessage("You recharge your prayer points.");
				c.getPA().refreshSkill(5);
			} else {
				c.sendMessage("You already have full prayer points.");
			}
			break;

		/**
		 * Aquring god capes.
		 */
		case 2873:
			if (!c.getItems().ownsCape()) {
				c.startAnimation(645);
				c.sendMessage("Saradomin blesses you with a cape.");
				c.getItems().addItem(2412, 1);
			} else {
				c.sendMessage("You already have a cape");
			}
			break;
		case 2875:
			if (!c.getItems().ownsCape()) {
				c.startAnimation(645);
				c.sendMessage("Guthix blesses you with a cape.");
				c.getItems().addItem(2413, 1);
			} else {
				c.sendMessage("You already have a cape");
			}
			break;
		case 2874:
			if (!c.getItems().ownsCape()) {
				c.startAnimation(645);
				c.sendMessage("Zamorak blesses you with a cape.");
				c.getItems().addItem(2414, 1);
			} else {
				c.sendMessage("You already have a cape");
			}
			break;

		/**
		 * Oblisks in the wilderness.
		 */
		case 14829:
		case 14830:
		case 14827:
		case 14828:
		case 14826:
		case 14831:
			Server.objectManager.startObelisk(objectType);
			break;

		/**
		 * Clicking certain doors.
		 */
		case 6749:
			if (obX == 3562 && obY == 9678) {
				c.getPA().object(3562, 9678, 6749, -3, 0);
				c.getPA().object(3562, 9677, 6730, -1, 0);
			} else if (obX == 3558 && obY == 9677) {
				c.getPA().object(3558, 9677, 6749, -1, 0);
				c.getPA().object(3558, 9678, 6730, -3, 0);
			}
			break;

		case 6730:
			if (obX == 3558 && obY == 9677) {
				c.getPA().object(3562, 9678, 6749, -3, 0);
				c.getPA().object(3562, 9677, 6730, -1, 0);
			} else if (obX == 3558 && obY == 9678) {
				c.getPA().object(3558, 9677, 6749, -1, 0);
				c.getPA().object(3558, 9678, 6730, -3, 0);
			}
			break;

		case 6727:
			if (obX == 3551 && obY == 9684) {
				c.sendMessage("You cant open this door..");
			}
			break;

		case 6746:
			if (obX == 3552 && obY == 9684) {
				c.sendMessage("You cant open this door..");
			}
			break;

		case 6748:
			if (obX == 3545 && obY == 9678) {
				c.getPA().object(3545, 9678, 6748, -3, 0);
				c.getPA().object(3545, 9677, 6729, -1, 0);
			} else if (obX == 3541 && obY == 9677) {
				c.getPA().object(3541, 9677, 6748, -1, 0);
				c.getPA().object(3541, 9678, 6729, -3, 0);
			}
			break;

		case 6729:
			if (obX == 3545 && obY == 9677) {
				c.getPA().object(3545, 9678, 6748, -3, 0);
				c.getPA().object(3545, 9677, 6729, -1, 0);
			} else if (obX == 3541 && obY == 9678) {
				c.getPA().object(3541, 9677, 6748, -1, 0);
				c.getPA().object(3541, 9678, 6729, -3, 0);
			}
			break;

		case 6726:
			if (obX == 3534 && obY == 9684) {
				c.getPA().object(3534, 9684, 6726, -4, 0);
				c.getPA().object(3535, 9684, 6745, -2, 0);
			} else if (obX == 3535 && obY == 9688) {
				c.getPA().object(3535, 9688, 6726, -2, 0);
				c.getPA().object(3534, 9688, 6745, -4, 0);
			}
			break;

		case 6745:
			if (obX == 3535 && obY == 9684) {
				c.getPA().object(3534, 9684, 6726, -4, 0);
				c.getPA().object(3535, 9684, 6745, -2, 0);
			} else if (obX == 3534 && obY == 9688) {
				c.getPA().object(3535, 9688, 6726, -2, 0);
				c.getPA().object(3534, 9688, 6745, -4, 0);
			}
			break;

		case 6743:
			if (obX == 3545 && obY == 9695) {
				c.getPA().object(3545, 9694, 6724, -1, 0);
				c.getPA().object(3545, 9695, 6743, -3, 0);
			} else if (obX == 3541 && obY == 9694) {
				c.getPA().object(3541, 9694, 6724, -1, 0);
				c.getPA().object(3541, 9695, 6743, -3, 0);
			}
			break;

		case 6724:
			if (obX == 3545 && obY == 9694) {
				c.getPA().object(3545, 9694, 6724, -1, 0);
				c.getPA().object(3545, 9695, 6743, -3, 0);
			} else if (obX == 3541 && obY == 9695) {
				c.getPA().object(3541, 9694, 6724, -1, 0);
				c.getPA().object(3541, 9695, 6743, -3, 0);
			}
			break;

		case 1516:
		case 1519:
			if (c.objectY == 9698) {
				if (c.absY >= c.objectY)
					c.getPA().walkTo(0, -1);
				else
					c.getPA().walkTo(0, 1);
				break;
			}
		case 1530:
		case 1531:
		case 1533:
		case 1534:
		case 11712:
		case 11711:
		case 11707:
		case 11708:
		case 6725:
		case 3198:
		case 3197:
			Server.objectHandler.doorHandling(objectType, c.objectX, c.objectY,
					0);
			break;

		case 5126:
			if (c.absY == 3554)
				c.getPA().walkTo(0, 1);
			else
				c.getPA().walkTo(0, -1);
			break;

		case 1759:
			if (c.objectX == 2884 && c.objectY == 3397)
				c.getPA().movePlayer(c.absX, c.absY + 6400, 0);
			break;
		case 1558:
			if (c.absX == 3041 && c.absY == 10308) {
				c.getPA().movePlayer(3040, 10308, 0);
			} else if (c.absX == 3040 && c.absY == 10308) {
				c.getPA().movePlayer(3041, 10308, 0);
			} else if (c.absX == 3040 && c.absY == 10307) {
				c.getPA().movePlayer(3041, 10307, 0);
			} else if (c.absX == 3041 && c.absY == 10307) {
				c.getPA().movePlayer(3040, 10307, 0);
			} else if (c.absX == 3044 && c.absY == 10341) {
				c.getPA().movePlayer(3045, 10341, 0);
			} else if (c.absX == 3045 && c.absY == 10341) {
				c.getPA().movePlayer(3044, 10341, 0);
			} else if (c.absX == 3044 && c.absY == 10342) {
				c.getPA().movePlayer(3045, 10342, 0);
			} else if (c.absX == 3045 && c.absY == 10342) {
				c.getPA().movePlayer(3044, 10343, 0);
			}
			break;
		case 1557:
			if (c.absX == 3023 && c.absY == 10312) {
				c.getPA().movePlayer(3022, 10312, 0);
			} else if (c.absX == 3022 && c.absY == 10312) {
				c.getPA().movePlayer(3023, 10312, 0);
			} else if (c.absX == 3023 && c.absY == 10311) {
				c.getPA().movePlayer(3022, 10311, 0);
			} else if (c.absX == 3022 && c.absY == 10311) {
				c.getPA().movePlayer(3023, 10311, 0);
			}
			break;
		case 2558:
			c.sendMessage("This door is locked.");
			break;

		case 9294:
			if (c.absX < c.objectX) {
				c.getPA().movePlayer(c.objectX + 1, c.absY, 0);
			} else if (c.absX > c.objectX) {
				c.getPA().movePlayer(c.objectX - 1, c.absY, 0);
			}
			break;

		case 9293:
			if (c.absX < c.objectX) {
				c.getPA().movePlayer(2892, 9799, 0);
			} else {
				c.getPA().movePlayer(2886, 9799, 0);
			}
			break;

		case 10529:
		case 10527:
			if (c.absY <= c.objectY)
				c.getPA().walkTo(0, 1);
			else
				c.getPA().walkTo(0, -1);
			break;

		case 733:
			c.startAnimation(451);

			if (c.objectX == 3158 && c.objectY == 3951) {
				new Object(734, c.objectX, c.objectY, c.heightLevel, 1, 10,
						733, 50);
			} else {
				new Object(734, c.objectX, c.objectY, c.heightLevel, 0, 10,
						733, 50);
			}
			break;

		default:
			ScriptManager.callFunc("objectClick1_" + objectType, c, objectType,
					obX, obY);
			break;

		/**
		 * Forfeiting a duel.
		 */
		case 3203:
			c.sendMessage("Forfeiting has been disabled.");
			break;

		}
	}

	public void secondClickObject(int objectType, int obX, int obY) {
		c.clickObjectType = 0;
		switch (objectType) {
		case 2558:
        case 2557:
            if (System.currentTimeMillis() - c.lastLockPick < 1000 || c.freezeTimer > 0)
            {
            	return;
            }
            c.lastLockPick = System.currentTimeMillis();
            if (c.getItems().playerHasItem(1523, 1))
            {
                
                if (Misc.random(10) <= 3)
                {
                    c.sendMessage("You fail to pick the lock.");
                    break;
                }
                if (c.objectX == 3044 && c.objectY == 3956)
                {
                    if (c.absX == 3045)
                    {
                        c.getPA().walkTo(-1, 0);
                    }
                    else if (c.absX == 3044)
                    {
                        c.getPA().walkTo(1, 0);
                    }

                }
                else if (c.objectX == 3038 && c.objectY == 3956)
                {
                    if (c.absX == 3037)
                    {
                        c.getPA().walkTo(1, 0);
                    }
                    else if (c.absX == 3038)
                    {
                        c.getPA().walkTo(-1, 0);
                    }
                }
                else if (c.objectX == 3041 && c.objectY == 3959)
                {
                    if (c.absY == 3960)
                    {
                        c.getPA().walkTo(0, -1);
                    }
                    else if (c.absY == 3959)
                    {
                        c.getPA().walkTo(0, 1);
                    }
                }
                else if (c.objectX == 3191 && c.objectY == 3963)
                {
                    if (c.absY == 3963)
                    {
                        c.getPA().walkTo(0, -1);
                    }
                    else if (c.absY == 3962)
                    {
                        c.getPA().walkTo(0, 1);
                    }
                }
                else if (c.objectX == 3190 && c.objectY == 3957)
                {
                    if (c.absY == 3957)
                    {
                        c.getPA().walkTo(0, 1);
                    }
                    else if (c.absY == 3958)
                    {
                        c.getPA().walkTo(0, -1);
                    }
                }
            }
            else
            {
                c.sendMessage("I need a lockpick to pick this lock.");
            }
			break;
		case 17010:
			if (c.playerMagicBook == 0) {
				c.sendMessage("You switch spellbook to lunar magic.");
				c.setSidebarInterface(6, 29999);
				c.playerMagicBook = 2;
				c.autocasting = false;
				c.autocastId = -1;
				c.getPA().resetAutocast();
				break;
			}
			if (c.playerMagicBook == 1) {
				c.sendMessage("You switch spellbook to lunar magic.");
				c.setSidebarInterface(6, 29999);
				c.playerMagicBook = 2;
				c.autocasting = false;
				c.autocastId = -1;
				c.getPA().resetAutocast();
				break;
			}
			if (c.playerMagicBook == 2) {
				c.setSidebarInterface(6, 1151);
				c.playerMagicBook = 0;
				c.autocasting = false;
				c.sendMessage("You feel a drain on your memory.");
				c.autocastId = -1;
				c.getPA().resetAutocast();
				break;
			}
			break;
		case 2781:
		case 26814:
		case 11666:
		case 3044:
			c.getSmithing().sendSmelting();
			break;
		case 2565:
			Thieving.stealFromStall(c, 995, 30000, 400, 50, objectType, obX,
					obY, 2);
			break;
		case 2564:
			Thieving.stealFromStall(c, 995, 40000, 500, 65, objectType, obX,
					obY, 0);
			break;
		case 2563:
			Thieving.stealFromStall(c, 995, 20000, 300, 36, objectType, obX,
					obY, 0);
			break;
		case 2562:
			Thieving.stealFromStall(c, 995, 50000, 600, 75, objectType, obX,
					obY, 3);
			break;
		case 2561:
			Thieving.stealFromStall(c, 995, 10000, 100, 1, objectType, obX,
					obY, obX == 2667 ? 3 : 1);
			break;
		case 2560:
			Thieving.stealFromStall(c, 995, 1000, 24, 20, objectType, obX, obY,
					obX == 2662 ? 2 : 1);
			break;

		case 14011:
			Thieving.stealFromStall(c, 995, 1000, 10, 1, objectType, obX, obY,
					3);
			break;
		case 7053:
			Thieving.stealFromStall(c, 995, 10000, 18, 10, objectType, obX,
					obY, obX == 3079 ? 2 : 1);
			break;
		case 2646:
			Flax.pickFlax(c, obX, obY);
			break;

		/**
		 * Opening the bank.
		 */
		case 2213:
		case 14367:
		case 11758:
		case 10517:
		case 26972:
			c.getPA().openUpBank();
			break;

		}
	}

	public void thirdClickObject(int objectType, int obX, int obY) {
		c.clickObjectType = 0;
		c.sendMessage("Object type: " + objectType);
		switch (objectType) {
		default:
			ScriptManager.callFunc("objectClick3_" + objectType, c, objectType,
					obX, obY);
			break;
		}
	}

	public void firstClickNpc(int npcType) {
		c.clickNpcType = 0;
		c.npcClickIndex = 0;
		/*
		 * if(Fishing.fishingNPC(c, npcType)) { Fishing.fishingNPC(c, 1,
		 * npcType); return; }
		 */
		switch (npcType) {
		// FISHING
		case 319:
		case 329:
		case 323:
			// case 325:
		case 326:
		case 327:
		case 330:
		case 332:
		case 316: // NET + BAIT
			Fishing.attemptdata(c, 1);
			break;
		case 325:
			Fishing.attemptdata(c, 12);
			break;
		case 334:
		case 313: // NET + HARPOON
			Fishing.attemptdata(c, 3);
			break;
		case 322: // NET + HARPOON
			Fishing.attemptdata(c, 5);
			break;

		case 309: // LURE
		case 310:
		case 311:
		case 314:
		case 315:
		case 317:
		case 318:
		case 328:
		case 331:
			Fishing.attemptdata(c, 4);
			break;

		case 312:
		case 321:
		case 324: // CAGE + HARPOON
			Fishing.attemptdata(c, 8);
			break;

		case 944:
			c.getDH().sendOption5("Hill Giants", "Hellhounds", "Lesser Demons",
					"Chaos Dwarf", "-- Next Page --");
			c.teleAction = 7;
			break;
		case 659:
			c.getShops().openShop(11);
			break;

		case 528:
			c.getShops().openShop(1);
			break;

		case 1599:
			if (c.slayerTask <= 0) {
				c.getDH().sendDialogues(11, npcType);
				// c.sendMessage("Slayer will be enabled in some minutes.");
			} else {
				c.sendMessage("You must complete or reset your slayer task before start another.");
			}
			break;

		case 953: // Banker
		case 2574: // Banker
		case 166: // Gnome Banker
		case 1702: // Ghost Banker
		case 494: // Banker
		case 495: // Banker
		case 496: // Banker
		case 497: // Banker
		case 498: // Banker
		case 499: // Banker
		case 567: // Banker
		case 1036: // Banker
		case 1360: // Banker
		case 2163: // Banker
		case 2164: // Banker
		case 2354: // Banker
		case 2355: // Banker
		case 2568: // Banker
		case 2569: // Banker
		case 2570: // Banker
			c.getDH().sendDialogues(1013, c.npcType);
			break;
		case 1986:
			c.getDH().sendDialogues(2244, c.npcType);
			break;

		case 644:
			c.getShops().openShop(9);
			c.sendMessage("You currently have @red@" + c.donPoints
					+ " @bla@donator points.");
			break;

		case 550:
			c.getShops().openShop(4);
			break;

		case 1785:
			c.getShops().openShop(8);
			break;

		case 1860:
			c.getShops().openShop(6);
			break;

		case 519:
			c.getShops().openShop(7);
			break;

		case 548:
			c.getDH().sendDialogues(69, c.npcType);
			break;

		case 2258:
			c.getDH().sendOption2("Teleport me to Runecrafting Abyss.",
					"I want to stay here, thanks.");
			c.dialogueAction = 2258;
			break;

		case 546:
			// c.getShops().openShop(5);
			c.getDH().sendOption4("Buy 1,000 barrage spells [2230k]",
					"Buy 1,000 vengeance spells [912k]",
					"Buy 1,000 of all runes [1788k]", "Open mage shop");
			c.dialogueAction = 1658;
			break;

		case 549:
			c.getShops().openShop(3);
			break;

		case 541:
			c.getShops().openShop(2);
			break;

		case 198:
			c.getShops().openSkillCape();
			break;

		/**
		 * Make over mage.
		 */

		}
	}

	public void secondClickNpc(int npcType) {
		c.clickNpcType = 0;
		c.npcClickIndex = 0;
		if (Thieving.pickpocketNPC(c, npcType)) {
			Thieving.attemptToPickpocket(c, npcType);
			return;
		}
		/*
		 * if(Fishing.fishingNPC(c, npcType)) { Fishing.fishingNPC(c, 2,
		 * npcType); return; }
		 */
		switch (npcType) {
		case 326:
		case 327:
		case 330:
		case 332:
		case 316: // BAIT + NET
			Fishing.attemptdata(c, 2);
			break;
		case 319:
		case 323:
		case 325: // BAIT + NET
			Fishing.attemptdata(c, 9);
			break;
		case 310:
		case 311:
		case 314:
		case 315:
		case 317:
		case 318:
		case 328:
		case 329:
		case 331:
		case 309: // BAIT + LURE
			Fishing.attemptdata(c, 6);
			break;
		case 312:
		case 321:
		case 324:// SWORDIES+TUNA-CAGE+HARPOON
			Fishing.attemptdata(c, 7);
			break;
		case 313:
		case 322:
		case 334: // NET+HARPOON
			Fishing.attemptdata(c, 10);
			break;
		case 546:
			c.getShops().openShop(5);
			break;
		case 1599:
			c.getShops().openShop(10);
			c.sendMessage("You currently have @red@" + c.slayerPoints
					+ " @bla@slayer points.");
			break;
		case 953: // Banker
		case 2574: // Banker
		case 166: // Gnome Banker
		case 1702: // Ghost Banker
		case 494: // Banker
		case 495: // Banker
		case 496: // Banker
		case 497: // Banker
		case 498: // Banker
		case 499: // Banker
		case 567: // Banker
		case 1036: // Banker
		case 1360: // Banker
		case 2163: // Banker
		case 2164: // Banker
		case 2354: // Banker
		case 2355: // Banker
		case 2568: // Banker
		case 2569: // Banker
		case 2570: // Banker
			c.getPA().openUpBank();
			break;

		case 1785:
			c.getShops().openShop(8);
			break;

		case 550:
			c.getShops().openShop(4);
			break;

		case 3796:
			c.getShops().openShop(6);
			break;

		case 1860:
			c.getShops().openShop(6);
			break;

		case 519:
			c.getShops().openShop(7);
			break;

		case 548:
			c.getDH().sendDialogues(69, c.npcType);
			break;

		case 2258:
			c.sendMessage("This NPC do not have a shop so you cant trade him.");
			// c.getPA().startTeleport(3039, 4834, 0, "modern"); //first click
			// teleports second click open shops
			break;

		case 541:
			c.getShops().openShop(2);
			break;

		case 528:
			c.getShops().openShop(1);
			break;

		case 532:
			c.getShops().openShop(1);
			break;
		}
	}

	public void thirdClickNpc(int npcType) {
		c.clickNpcType = 0;
		c.npcClickIndex = 0;
		switch (npcType) {
		case 1599:
			c.getShops().openShop(10);
			c.sendMessage("You currently have @red@" + c.slayerPoints
					+ " @bla@slayer points.");
			break;
		case 548:
			if (!c.ardiRizal()) {
				c.sendMessage("You must remove your equipment before changing your appearence.");
				c.canChangeAppearance = false;
			} else {
				c.getPA().showInterface(3559);
				c.canChangeAppearance = true;
			}
			break;

		case 836:
			c.getShops().openShop(103);
			break;

		}
	}

}