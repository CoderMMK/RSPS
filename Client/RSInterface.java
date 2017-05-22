public class RSInterface {

	private boolean interfaceShown;

	public void swapInventoryItems(int i, int j) {
		int k = inv[i];
		inv[i] = inv[j];
		inv[j] = k;
		k = invStackSizes[i];
		invStackSizes[i] = invStackSizes[j];
		invStackSizes[j] = k;
	}

	public static void unpack(StreamLoader streamLoader,
			TextDrawingArea textDrawingAreas[], StreamLoader streamLoader_1) {
		aMRUNodes_238 = new MRUNodes(50000);
		Stream stream = new Stream(streamLoader.getDataForName("data"));
		int i = -1;
		int j = stream.readUnsignedWord();
		interfaceCache = new RSInterface[j + 50000];
		while (stream.currentOffset < stream.buffer.length) {
			int k = stream.readUnsignedWord();
			if (k == 65535) {
				i = stream.readUnsignedWord();
				k = stream.readUnsignedWord();
			}
			RSInterface rsInterface = interfaceCache[k] = new RSInterface();
			rsInterface.id = k;
			rsInterface.parentID = i;
			rsInterface.type = stream.readUnsignedByte();
			rsInterface.atActionType = stream.readUnsignedByte();
			rsInterface.contentType = stream.readUnsignedWord();
			rsInterface.width = stream.readUnsignedWord();
			rsInterface.height = stream.readUnsignedWord();
			rsInterface.aByte254 = (byte) stream.readUnsignedByte();
			rsInterface.mOverInterToTrigger = stream.readUnsignedByte();
			if (rsInterface.mOverInterToTrigger != 0)
				rsInterface.mOverInterToTrigger = (rsInterface.mOverInterToTrigger - 1 << 8)
						+ stream.readUnsignedByte();
			else
				rsInterface.mOverInterToTrigger = -1;
			int i1 = stream.readUnsignedByte();
			if (i1 > 0) {
				rsInterface.anIntArray245 = new int[i1];
				rsInterface.anIntArray212 = new int[i1];
				for (int j1 = 0; j1 < i1; j1++) {
					rsInterface.anIntArray245[j1] = stream.readUnsignedByte();
					rsInterface.anIntArray212[j1] = stream.readUnsignedWord();
				}

			}
			int k1 = stream.readUnsignedByte();
			if (k1 > 0) {
				rsInterface.valueIndexArray = new int[k1][];
				for (int l1 = 0; l1 < k1; l1++) {
					int i3 = stream.readUnsignedWord();
					rsInterface.valueIndexArray[l1] = new int[i3];
					for (int l4 = 0; l4 < i3; l4++)
						rsInterface.valueIndexArray[l1][l4] = stream
								.readUnsignedWord();

				}

			}
			if (rsInterface.type == 0) {
				rsInterface.drawsTransparent = false;
				rsInterface.scrollMax = stream.readUnsignedWord();
				rsInterface.isMouseoverTriggered = stream.readUnsignedByte() == 1;
				int i2 = stream.readUnsignedWord();
				rsInterface.children = new int[i2];
				rsInterface.childX = new int[i2];
				rsInterface.childY = new int[i2];
				for (int j3 = 0; j3 < i2; j3++) {
					rsInterface.children[j3] = stream.readUnsignedWord();
					rsInterface.childX[j3] = stream.readSignedWord();
					rsInterface.childY[j3] = stream.readSignedWord();
				}
			}
			if (rsInterface.type == 1) {
				stream.readUnsignedWord();
				stream.readUnsignedByte();
			}
			if (rsInterface.type == 2) {
				rsInterface.inv = new int[rsInterface.width
						* rsInterface.height];
				rsInterface.invStackSizes = new int[rsInterface.width
						* rsInterface.height];
				rsInterface.aBoolean259 = stream.readUnsignedByte() == 1;
				rsInterface.isInventoryInterface = stream.readUnsignedByte() == 1;
				rsInterface.usableItemInterface = stream.readUnsignedByte() == 1;
				rsInterface.aBoolean235 = stream.readUnsignedByte() == 1;
				rsInterface.invSpritePadX = stream.readUnsignedByte();
				rsInterface.invSpritePadY = stream.readUnsignedByte();
				rsInterface.spritesX = new int[20];
				rsInterface.spritesY = new int[20];
				rsInterface.sprites = new Sprite[20];
				for (int j2 = 0; j2 < 20; j2++) {
					int k3 = stream.readUnsignedByte();
					if (k3 == 1) {
						rsInterface.spritesX[j2] = stream.readSignedWord();
						rsInterface.spritesY[j2] = stream.readSignedWord();
						String s1 = stream.readString();
						if (streamLoader_1 != null && s1.length() > 0) {
							int i5 = s1.lastIndexOf(",");
							rsInterface.sprites[j2] = method207(
									Integer.parseInt(s1.substring(i5 + 1)),
									streamLoader_1, s1.substring(0, i5));
						}
					}
				}
				rsInterface.actions = new String[5];
				for (int l3 = 0; l3 < 5; l3++) {
					rsInterface.actions[l3] = stream.readString();
					if (rsInterface.actions[l3].length() == 0)
						rsInterface.actions[l3] = null;
					if (rsInterface.parentID == 3824)
						rsInterface.actions[4] = "Buy 100";
					if (rsInterface.parentID == 1644)
						rsInterface.actions[2] = "Operate";
				}
			}
			if (rsInterface.type == 3)
				rsInterface.aBoolean227 = stream.readUnsignedByte() == 1;
			if (rsInterface.type == 4 || rsInterface.type == 1) {
				rsInterface.centerText = stream.readUnsignedByte() == 1;
				int k2 = stream.readUnsignedByte();
				if (textDrawingAreas != null)
					rsInterface.textDrawingAreas = textDrawingAreas[k2];
				rsInterface.textShadow = stream.readUnsignedByte() == 1;
			}
			if (rsInterface.type == 4) {
				rsInterface.message = stream.readString();
				rsInterface.aString228 = stream.readString();
			}
			if (rsInterface.type == 1 || rsInterface.type == 3
					|| rsInterface.type == 4)
				rsInterface.textColor = stream.readDWord();
			if (rsInterface.type == 3 || rsInterface.type == 4) {
				rsInterface.anInt219 = stream.readDWord();
				rsInterface.anInt216 = stream.readDWord();
				rsInterface.anInt239 = stream.readDWord();
			}
			if (rsInterface.type == 5) {
				rsInterface.drawsTransparent = false;
				String s = stream.readString();
				if (streamLoader_1 != null && s.length() > 0) {
					int i4 = s.lastIndexOf(",");
					rsInterface.sprite1 = method207(
							Integer.parseInt(s.substring(i4 + 1)),
							streamLoader_1, s.substring(0, i4));
				}
				s = stream.readString();
				if (streamLoader_1 != null && s.length() > 0) {
					int j4 = s.lastIndexOf(",");
					rsInterface.sprite2 = method207(
							Integer.parseInt(s.substring(j4 + 1)),
							streamLoader_1, s.substring(0, j4));
				}
			}
			if (rsInterface.type == 6) {
				int l = stream.readUnsignedByte();
				if (l != 0) {
					rsInterface.anInt233 = 1;
					rsInterface.mediaID = (l - 1 << 8)
							+ stream.readUnsignedByte();
				}
				l = stream.readUnsignedByte();
				if (l != 0) {
					rsInterface.anInt255 = 1;
					rsInterface.anInt256 = (l - 1 << 8)
							+ stream.readUnsignedByte();
				}
				l = stream.readUnsignedByte();
				if (l != 0)
					rsInterface.anInt257 = (l - 1 << 8)
							+ stream.readUnsignedByte();
				else
					rsInterface.anInt257 = -1;
				l = stream.readUnsignedByte();
				if (l != 0)
					rsInterface.anInt258 = (l - 1 << 8)
							+ stream.readUnsignedByte();
				else
					rsInterface.anInt258 = -1;
				rsInterface.modelZoom = stream.readUnsignedWord();
				rsInterface.modelRotation1 = stream.readUnsignedWord();
				rsInterface.modelRotation2 = stream.readUnsignedWord();
			}
			if (rsInterface.type == 7) {
				rsInterface.inv = new int[rsInterface.width
						* rsInterface.height];
				rsInterface.invStackSizes = new int[rsInterface.width
						* rsInterface.height];
				rsInterface.centerText = stream.readUnsignedByte() == 1;
				int l2 = stream.readUnsignedByte();
				if (textDrawingAreas != null)
					rsInterface.textDrawingAreas = textDrawingAreas[l2];
				rsInterface.textShadow = stream.readUnsignedByte() == 1;
				rsInterface.textColor = stream.readDWord();
				rsInterface.invSpritePadX = stream.readSignedWord();
				rsInterface.invSpritePadY = stream.readSignedWord();
				rsInterface.isInventoryInterface = stream.readUnsignedByte() == 1;
				rsInterface.actions = new String[5];
				for (int k4 = 0; k4 < 5; k4++) {
					rsInterface.actions[k4] = stream.readString();
					if (rsInterface.actions[k4].length() == 0)
						rsInterface.actions[k4] = null;
				}

			}
			if (rsInterface.atActionType == 2 || rsInterface.type == 2) {
				rsInterface.selectedActionName = stream.readString();
				rsInterface.spellName = stream.readString();
				rsInterface.spellUsableOn = stream.readUnsignedWord();
			}

			if (rsInterface.type == 8)
				rsInterface.message = stream.readString();

			if (rsInterface.atActionType == 1 || rsInterface.atActionType == 4
					|| rsInterface.atActionType == 5
					|| rsInterface.atActionType == 6) {
				rsInterface.tooltip = stream.readString();
				if (rsInterface.tooltip.length() == 0) {
					if (rsInterface.atActionType == 1)
						rsInterface.tooltip = "Ok";
					if (rsInterface.atActionType == 4)
						rsInterface.tooltip = "Select";
					if (rsInterface.atActionType == 5)
						rsInterface.tooltip = "Select";
					if (rsInterface.atActionType == 6)
						rsInterface.tooltip = "Continue";
				}
			}
		}
		aClass44 = streamLoader;
		questTab(textDrawingAreas);
		bank(textDrawingAreas);
		itemsKeptOnDeath(textDrawingAreas);
		itemsOnDeathDATA(textDrawingAreas);
		itemsOnDeath(textDrawingAreas);
		equipmentTab(textDrawingAreas);
		Interfaces.loadInterfaces(streamLoader, textDrawingAreas);
		aMRUNodes_238 = null;
	}

	public static void bank(TextDrawingArea[] TDA) {
		RSInterface Tab = addTabInterface(23000);
		addText(23003, "", TDA, 0, 0xFFB000, false, false);
		addHover(23004, 3, 0, 23005, 1, "Bank/BANK", 16, 16, "Close");
		addHovered(23005, 2, "Bank/BANK", 16, 16, 23006);
		addHover(23007, 4, 0, 23008, 17, "Bank/BANK", 35, 25,
				"Deposit carried items");
		addHovered(23008, 18, "Bank/BANK", 35, 25, 23009);
		Tab.children = new int[7];
		Tab.childX = new int[7];
		Tab.childY = new int[7];
		Tab.children[0] = 5292;
		Tab.childX[0] = 0;
		Tab.childY[0] = 0;
		Tab.children[1] = 23003;
		Tab.childX[1] = 410;
		Tab.childY[1] = 30;
		Tab.children[2] = 23004;
		Tab.childX[2] = 472;
		Tab.childY[2] = 29;
		Tab.children[3] = 23005;
		Tab.childX[3] = 472;
		Tab.childY[3] = 29;
		Tab.children[4] = 23007;
		Tab.childX[4] = 450;
		Tab.childY[4] = 288;
		Tab.children[5] = 23008;
		Tab.childX[5] = 450;
		Tab.childY[5] = 288;
		Tab.children[6] = 23008;
		Tab.childX[6] = 3000;
		Tab.childY[6] = 5000;
		RSInterface rsi = interfaceCache[5292];
		addText(5384, "", TDA, 0, 0xFFB000, false, false);// cheap hax
		rsi.childX[90] = 410;
		rsi.childY[90] = 288;
	}

	public static void drawTooltip(int id, String text) {
		RSInterface rsinterface = addTabInterface(id);
		rsinterface.parentID = id;
		rsinterface.type = 0;
		rsinterface.isMouseoverTriggered = true;
		rsinterface.mOverInterToTrigger = -1;
		addTooltipBox(id + 1, text);
		rsinterface.totalChildren(1);
		rsinterface.child(0, id + 1, 0, 0);
	}

	public static void addButton(int i, int j, String name, int W, int H,
			String S, int AT) {
		RSInterface RSInterface = addInterface(i);
		RSInterface.id = i;
		RSInterface.parentID = i;
		RSInterface.type = 5;
		RSInterface.atActionType = AT;
		RSInterface.contentType = 0;
		RSInterface.opacity = 0;
		RSInterface.hoverType = 52;
		RSInterface.sprite1 = imageLoader(j, name);
		RSInterface.sprite2 = imageLoader(j, name);
		RSInterface.width = W;
		RSInterface.height = H;
		RSInterface.tooltip = S;
	}

	public boolean newScroller;

	public static void questTab(TextDrawingArea[] TDA) {
		RSInterface Interface = addInterface(638);
		setChildren(4, Interface);
		addText(29155, "@cr2@ Ardi", 0xFF981F, false, true, 52, TDA, 2);
		addButton(29156, 1, "Interfaces/QuestTab/QUEST", 18, 18,
				"Swap to Achievements", 1);
		addSprite(29157, 0, "Interfaces/QuestTab/QUEST");
		setBounds(29155, 10, 5, 0, Interface);
		setBounds(29156, 165, 5, 1, Interface);
		setBounds(29157, 3, 24, 2, Interface);
		setBounds(29160, 5, 29, 3, Interface);
		Interface = addInterface(29160);
		Interface.height = 214;
		Interface.width = 165;
		Interface.scrollMax = 1700;
		Interface.newScroller = false;
		setChildren(105, Interface);
		addText(29161, "Player", 0xFF981F, false, true, 52, TDA, 2);
		addHoverText(29162, "", "View Progress", TDA, 0,
				0xff0000, false, true, 150);
		addHoverText(29163, "", "View Progress", TDA, 0,
				0xff0000, false, true, 150);
		addHoverText(29164, "", "View Progress", TDA, 0,
				0xff0000, false, true, 150);
		addHoverText(29165, "", "View Progress", TDA, 0,
				0xff0000, false, true, 150);
		addHoverText(29166, "", "View Progress", TDA, 0, 0xff0000,
				false, true, 150);
		setBounds(29161, 4, 4, 0, Interface);
		setBounds(29162, 8, 22, 1, Interface);
		setBounds(29163, 8, 35, 2, Interface);
		setBounds(29164, 8, 48, 3, Interface);
		setBounds(29165, 8, 61, 4, Interface);
		setBounds(29166, 8, 74, 5, Interface);
		setBounds(663, 4, 90, 6, Interface);
		int Ypos = 108;
		int frameID = 7;
		for (int iD = 29167; iD <= 29264; iD++) {
			addHoverText(iD, "", "View"/* "View Quest Journal, "+iD */, TDA,
					0, 0xff0000, false, true, 150);
			setBounds(iD, 8, Ypos, frameID, Interface);
			frameID++;
			Ypos += 13;
			Ypos++;
		}
		Interface = addInterface(29265);
		try {
			setChildren(4, Interface);
			addText(29266, "        Achievements", 0xFF981F, false, true, -1,
					TDA, 2);
			addButton(29267, 2, "Interfaces/QuestTab/QUEST", 18, 18,
					"Swap to Quests", 1);
			addSprite(29269, 0, "Interfaces/QuestTab/QUEST");
			setBounds(29266, 10, 5, 0, Interface);
			setBounds(29267, 165, 5, 1, Interface);
			setBounds(29269, 3, 24, 2, Interface);
			setBounds(29268, 5, 29, 3, Interface);
			Interface = addInterface(29268);
			Interface.height = 214;
			Interface.width = 165;
			Interface.scrollMax = 215;
			Interface.newScroller = false;
			setChildren(9, Interface);
			setBounds(29295, 8, 6, 0, Interface);
			setBounds(29287, 8, 21, 1, Interface);
			setBounds(29305, 8, 36, 2, Interface);
			setBounds(29306, 8, 51, 3, Interface);
			setBounds(29307, 8, 66, 4, Interface);
			setBounds(29308, 8, 81, 5, Interface);
			setBounds(29309, 8, 96, 6, Interface);
			setBounds(29310, 8, 110, 7, Interface);
			setBounds(29311, 8, 125, 8, Interface);
			addHoverText(29295, "Tasks Completed 0/6", "View Achievements",
					TDA, 0, 65280, false, true, 150);
			addHoverText(29287, "Easy Tasks", "View Achievements", TDA, 0,
					0xFF981F, false, true, 150);
			addHoverText(29305, "Light 100 Fires", "View Achievements", TDA, 0,
					0xff0000, false, true, 150);
			addHoverText(29306, "Catch 100 Fish", "View Achievements", TDA, 0,
					0xff0000, false, true, 150);
			addHoverText(29307, "Kill 30 Rockcrabs", "View Achievements", TDA,
					0, 0xff0000, false, true, 150);
			addHoverText(29308, "Chop 100 Tress", "View Achievements", TDA, 0,
					0xff0000, false, true, 150);
			addHoverText(29309, "Earn 35 Pk Points", "View Achievements", TDA,
					0, 0xff0000, false, true, 150);
			addHoverText(29310, "Medium Tasks", "View Achievements", TDA, 0,
					0xFF981F, false, true, 150);
			addHoverText(29311, "Coming Soon...", "View Achievements", TDA, 0,
					0xff0000, false, true, 150);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void equipmentTab(TextDrawingArea[] wid) {
		RSInterface Interface = interfaceCache[1644];
		addSprite(15101, 0, "Interfaces/Equipment/bl");// cheap hax
		addSprite(15102, 1, "Interfaces/Equipment/bl");// cheap hax
		addSprite(15109, 2, "Interfaces/Equipment/bl");// cheap hax
		// removeSomething(15103);
		// removeSomething(15104);
		Interface.children[23] = 15101;
		Interface.childX[23] = 40;
		Interface.childY[23] = 205;
		Interface.children[24] = 15102;
		Interface.childX[24] = 110;
		Interface.childY[24] = 205;
		Interface.children[25] = 15109;
		Interface.childX[25] = 39;
		Interface.childY[25] = 240;
		Interface.children[26] = 27650;
		Interface.childX[26] = 0;
		Interface.childY[26] = 0;
		Interface = addInterface(27650);
		AddInterfaceButton(27651, 2, "Interfaces/Equipment/BOX",
				"Price-checker", 27659, 1, 40, 39);
		addTooltip(27659, "Price-checker");
		AddInterfaceButton(27653, 1, "Interfaces/Equipment/BOX",
				"Show Equipment Stats", 27655, 1, 40, 39);
		addTooltip(27655, "Show Equipment Stats");
		AddInterfaceButton(27654, 3, "Interfaces/Equipment/BOX",
				"Show items kept on death", 27657, 1, 40, 39);
		addTooltip(27657, "Items kept on death");
		setChildren(6, Interface);
		setBounds(27651, 75, 205, 0, Interface);
		setBounds(27653, 23, 205, 1, Interface);
		setBounds(27654, 127, 205, 2, Interface);
		setBounds(27659, 39, 240, 3, Interface);
		setBounds(27655, 39, 240, 4, Interface);
		setBounds(27657, 39, 240, 5, Interface);
	}

	public static void addBankHover1(int interfaceID, int actionType,
			int hoverid, int spriteId, String NAME, int Width, int Height,
			String Tooltip, int hoverId2, int hoverSpriteId,
			String hoverSpriteName, int hoverId3, String hoverDisabledText,
			int X, int Y) {
		RSInterface hover = addTabInterface(interfaceID);
		hover.id = interfaceID;
		hover.parentID = interfaceID;
		hover.type = 5;
		hover.atActionType = actionType;
		hover.contentType = 0;
		hover.aByte254 = 0;
		hover.sprite1 = imageLoader(spriteId, NAME);
		hover.width = Width;
		hover.tooltip = Tooltip;
		hover.height = Height;
		hover = addTabInterface(hoverid);
		hover.parentID = hoverid;
		hover.id = hoverid;
		hover.type = 0;
		hover.atActionType = 0;
		hover.width = 550;
		hover.height = 334;
		hover.isMouseoverTriggered = true;
		addSprite(hoverId2, hoverSpriteId, hoverSpriteId, hoverSpriteName, 0, 0);
		addHoverBox(hoverId3, interfaceID, hoverDisabledText,
				hoverDisabledText, 0, 0);
		setChildren(2, hover);
		setBounds(hoverId2, 15, 60, 0, hover);
		setBounds(hoverId3, X, Y, 1, hover);
	}

	public static void itemsOnDeath(TextDrawingArea[] wid) {
		RSInterface rsinterface = addInterface(17100);
		addSprite(17101, 2, 2);
		// addHover(17102,"Items Kept On Death/SPRITE", 1, 17, 17, "Close", 0,
		// 10602, 1);
		// addHovered(10602,"Items Kept On Death/SPRITE", 3, 17, 17, 10603);
		addText(17103, "Items kept on death", wid, 2, 0xff981f);
		addText(17104, "Items I will keep...", wid, 1, 0xff981f);
		addText(17105, "Items I will lose...", wid, 1, 0xff981f);
		addText(17106, "Info", wid, 1, 0xff981f);
		addText(17107, "DethWish", wid, 1, 0xffcc33);
		addText(17108, "", wid, 1, 0xffcc33);
		// rsinterface.scrollMax = 50;
		rsinterface.interfaceShown = false;
		rsinterface.children = new int[12];
		rsinterface.childX = new int[12];
		rsinterface.childY = new int[12];

		rsinterface.children[0] = 17101;
		rsinterface.childX[0] = 7;
		rsinterface.childY[0] = 8;
		rsinterface.children[1] = 15210;
		rsinterface.childX[1] = 478;
		rsinterface.childY[1] = 17;
		rsinterface.children[2] = 17103;
		rsinterface.childX[2] = 185;
		rsinterface.childY[2] = 18;
		rsinterface.children[3] = 17104;
		rsinterface.childX[3] = 22;
		rsinterface.childY[3] = 49;
		rsinterface.children[4] = 17105;
		rsinterface.childX[4] = 22;
		rsinterface.childY[4] = 109;
		rsinterface.children[5] = 17106;
		rsinterface.childX[5] = 347;
		rsinterface.childY[5] = 49;
		rsinterface.children[6] = 17107;
		rsinterface.childX[6] = 348;
		rsinterface.childY[6] = 270;
		rsinterface.children[7] = 17108;
		rsinterface.childX[7] = 401;
		rsinterface.childY[7] = 293;
		rsinterface.children[8] = 17115;
		rsinterface.childX[8] = 348;
		rsinterface.childY[8] = 64;
		rsinterface.children[9] = 10494;
		rsinterface.childX[9] = 26;
		rsinterface.childY[9] = 71;
		rsinterface.children[10] = 10600;
		rsinterface.childX[10] = 26;
		rsinterface.childY[10] = 129;
		rsinterface.children[11] = 15211;
		rsinterface.childX[11] = 478;
		rsinterface.childY[11] = 17;
		rsinterface = interfaceCache[10494];
		rsinterface.invSpritePadX = 6;
		rsinterface.invSpritePadY = 5;
		rsinterface = interfaceCache[10600];
		rsinterface.invSpritePadX = 6;
		rsinterface.invSpritePadY = 5;
	}

	public static void itemsOnDeathDATA(TextDrawingArea[] tda) {
		RSInterface RSinterface = addInterface(17115);
		addText(17109, "", 0xff981f, false, false, 0, tda, 0);
		addText(17110, "The normal amount of", 0xff981f, false, false, 0, tda,
				0);
		addText(17111, "items kept is three.", 0xff981f, false, false, 0, tda,
				0);
		addText(17112, "", 0xff981f, false, false, 0, tda, 0);
		addText(17113, "If you are skulled,", 0xff981f, false, false, 0, tda, 0);
		addText(17114, "you will lose all your", 0xff981f, false, false, 0,
				tda, 0);
		addText(17117, "items, unless an item", 0xff981f, false, false, 0, tda,
				0);
		addText(17118, "protecting prayer is", 0xff981f, false, false, 0, tda,
				0);
		addText(17119, "used.", 0xff981f, false, false, 0, tda, 0);
		addText(17120, "", 0xff981f, false, false, 0, tda, 0);
		addText(17121, "Item protecting prayers", 0xff981f, false, false, 0,
				tda, 0);
		addText(17122, "will allow you to keep", 0xff981f, false, false, 0,
				tda, 0);
		addText(17123, "one extra item.", 0xff981f, false, false, 0, tda, 0);
		addText(17124, "", 0xff981f, false, false, 0, tda, 0);
		addText(17125, "The items kept are", 0xff981f, false, false, 0, tda, 0);
		addText(17126, "selected by the server", 0xff981f, false, false, 0,
				tda, 0);
		addText(17127, "and include the most", 0xff981f, false, false, 0, tda,
				0);
		addText(17128, "expensive items you're", 0xff981f, false, false, 0,
				tda, 0);
		addText(17129, "carrying.", 0xff981f, false, false, 0, tda, 0);
		addText(17130, "", 0xff981f, false, false, 0, tda, 0);
		RSinterface.parentID = 17115;
		RSinterface.id = 17115;
		RSinterface.type = 0;
		RSinterface.atActionType = 0;
		RSinterface.contentType = 0;
		RSinterface.width = 130;
		RSinterface.height = 197;
		RSinterface.opacity = 0;
		RSinterface.hoverType = -1;
		RSinterface.scrollMax = 280;
		RSinterface.children = new int[20];
		RSinterface.childX = new int[20];
		RSinterface.childY = new int[20];
		RSinterface.children[0] = 17109;
		RSinterface.childX[0] = 0;
		RSinterface.childY[0] = 0;
		RSinterface.children[1] = 17110;
		RSinterface.childX[1] = 0;
		RSinterface.childY[1] = 12;
		RSinterface.children[2] = 17111;
		RSinterface.childX[2] = 0;
		RSinterface.childY[2] = 24;
		RSinterface.children[3] = 17112;
		RSinterface.childX[3] = 0;
		RSinterface.childY[3] = 36;
		RSinterface.children[4] = 17113;
		RSinterface.childX[4] = 0;
		RSinterface.childY[4] = 48;
		RSinterface.children[5] = 17114;
		RSinterface.childX[5] = 0;
		RSinterface.childY[5] = 60;
		RSinterface.children[6] = 17117;
		RSinterface.childX[6] = 0;
		RSinterface.childY[6] = 72;
		RSinterface.children[7] = 17118;
		RSinterface.childX[7] = 0;
		RSinterface.childY[7] = 84;
		RSinterface.children[8] = 17119;
		RSinterface.childX[8] = 0;
		RSinterface.childY[8] = 96;
		RSinterface.children[9] = 17120;
		RSinterface.childX[9] = 0;
		RSinterface.childY[9] = 108;
		RSinterface.children[10] = 17121;
		RSinterface.childX[10] = 0;
		RSinterface.childY[10] = 120;
		RSinterface.children[11] = 17122;
		RSinterface.childX[11] = 0;
		RSinterface.childY[11] = 132;
		RSinterface.children[12] = 17123;
		RSinterface.childX[12] = 0;
		RSinterface.childY[12] = 144;
		RSinterface.children[13] = 17124;
		RSinterface.childX[13] = 0;
		RSinterface.childY[13] = 156;
		RSinterface.children[14] = 17125;
		RSinterface.childX[14] = 0;
		RSinterface.childY[14] = 168;
		RSinterface.children[15] = 17126;
		RSinterface.childX[15] = 0;
		RSinterface.childY[15] = 180;
		RSinterface.children[16] = 17127;
		RSinterface.childX[16] = 0;
		RSinterface.childY[16] = 192;
		RSinterface.children[17] = 17128;
		RSinterface.childX[17] = 0;
		RSinterface.childY[17] = 204;
		RSinterface.children[18] = 17129;
		RSinterface.childX[18] = 0;
		RSinterface.childY[18] = 216;
		RSinterface.children[19] = 17130;
		RSinterface.childX[19] = 0;
		RSinterface.childY[19] = 228;
	}

	public static void itemsKeptOnDeath(TextDrawingArea[] tda) {
		RSInterface Interface = addInterface(22030);
		addSprite(22031, 1, "Death/SPRITE");
		addHoverButton(22032, "Death/SPRITE", 2, 17, 17, "Close", 250, 22033, 3);
		addHoveredButton(22033, "Death/SPRITE", 3, 17, 17, 22034);
		addText(22035, "22035", tda, 0, 0xff981f, false, true);
		addText(22036, "22036", tda, 0, 0xff981f, false, true);
		addText(22037, "22037", tda, 0, 0xff981f, false, true);
		addText(22038, "22038", tda, 0, 0xff981f, false, true);
		addText(22039, "22039", tda, 0, 0xff981f, false, true);
		addText(22040, "22040", tda, 1, 0xffcc33, false, true);
		setChildren(9, Interface);
		setBounds(22031, 7, 8, 0, Interface);
		setBounds(22032, 480, 18, 1, Interface);
		setBounds(22033, 480, 18, 2, Interface);
		setBounds(22035, 348, 98, 3, Interface);
		setBounds(22036, 348, 110, 4, Interface);
		setBounds(22037, 348, 122, 5, Interface);
		setBounds(22038, 348, 134, 6, Interface);
		setBounds(22039, 348, 146, 7, Interface);
		setBounds(22040, 398, 297, 8, Interface);
	}

	public static void addTextButton(int i, String s, String tooltip, int k,
			boolean l, boolean m, TextDrawingArea[] TDA, int j, int w) {
		RSInterface rsinterface = addInterface(i);
		rsinterface.parentID = i;
		rsinterface.id = i;
		rsinterface.type = 4;
		rsinterface.atActionType = 1;
		rsinterface.width = w;
		rsinterface.height = 16;
		rsinterface.contentType = 0;
		rsinterface.aByte254 = (byte) 0xFF981F;
		rsinterface.mOverInterToTrigger = -1;
		rsinterface.centerText = l;
		rsinterface.textShadow = m;
		rsinterface.textDrawingAreas = TDA[j];
		rsinterface.message = s;
		rsinterface.aString228 = "";
		rsinterface.anInt219 = 0xFF981F;
		rsinterface.textColor = 0xFF981F;
		rsinterface.tooltip = tooltip;
	}

	public static void slayerInterface(TextDrawingArea[] tda) {
		RSInterface rsInterface = addInterface(41000);
		addSprite(41001, 1, "Interfaces/SlayerInterface/IMAGE");
		addHoverButton(41002, "Interfaces/SlayerInterface/IMAGE", 4, 16, 16,
				"Close window", 0, 41003, 1);
		addHoveredButton(41003, "Interfaces/SlayerInterface/IMAGE", 5, 16, 16,
				41004);
		addHoverButton(41005, "", 0, 85, 20, "Buy", 0, 41006, 1);
		addHoverButton(41007, "", 0, 85, 20, "Learn", 0, 41008, 1);
		addHoverButton(41009, "", 0, 85, 20, "Assignment", 0, 41010, 1);
		addText(41011, "Slayer Points: ", tda, 3, 0xFF981F);
		addTextButton(41012, "Slayer Experience                           50",
				"Buy Slayer Experience", 0xFF981F, false, true, tda, 1, 400);
		addTextButton(41013, "Slayer's Respite                             25",
				"Buy Slayer's Respite", 0xFF981F, false, true, tda, 1, 401);
		addTextButton(41014,
				"Slayer Darts                                     35",
				"Buy Slayer Darts", 0xFF981F, false, true, tda, 1, 402);
		addTextButton(41015,
				"Broad Arrows                                    25",
				"Buy Broad Arrows", 0xFF981F, false, true, tda, 1, 403);
		setChildren(11, rsInterface);
		rsInterface.child(0, 41001, 12, 10);
		rsInterface.child(1, 41002, 473, 20);
		rsInterface.child(2, 41003, 473, 20);
		rsInterface.child(3, 41005, 21, 23);
		rsInterface.child(4, 41007, 107, 23);
		rsInterface.child(5, 41009, 193, 23);
		rsInterface.child(6, 41011, 98, 74);
		rsInterface.child(7, 41012, 124, 128);
		rsInterface.child(8, 41013, 125, 160);
		rsInterface.child(9, 41014, 125, 190);
		rsInterface.child(10, 41015, 124, 220);

	}

	public static void slayerInterfaceSub1(TextDrawingArea[] tda) {
		RSInterface rsInterface = addInterface(41500);
		addSprite(41501, 2, "Interfaces/SlayerInterface/IMAGE");
		addHoverButton(41502, "Interfaces/SlayerInterface/IMAGE", 4, 16, 16,
				"Close window", 0, 41503, 1);
		addHoveredButton(41503, "Interfaces/SlayerInterface/IMAGE", 5, 16, 16,
				41504);
		addHoverButton(41505, "", 0, 85, 20, "Buy", 0, 41506, 1);
		addHoverButton(41507, "", 0, 85, 20, "Learn", 0, 41508, 1);
		addHoverButton(41509, "", 0, 85, 20, "Assignment", 0, 41510, 1);
		addText(41511, "Slayer Points: ", tda, 3, 0xFF981F);
		setChildren(7, rsInterface);
		rsInterface.child(0, 41501, 12, 10);
		rsInterface.child(1, 41502, 473, 20);
		rsInterface.child(2, 41503, 473, 20);
		rsInterface.child(3, 41505, 21, 23);
		rsInterface.child(4, 41507, 107, 23);
		rsInterface.child(5, 41509, 193, 23);
		rsInterface.child(6, 41511, 98, 74);
	}

	public static void slayerInterfaceSub2(TextDrawingArea[] tda) {
		RSInterface rsInterface = addInterface(42000);
		addSprite(42001, 3, "Interfaces/SlayerInterface/IMAGE");
		addHoverButton(42002, "Interfaces/SlayerInterface/IMAGE", 4, 16, 16,
				"Close window", 0, 42003, 1);
		addHoveredButton(42003, "Interfaces/SlayerInterface/IMAGE", 5, 16, 16,
				42004);
		addHoverButton(42005, "", 0, 85, 20, "Buy", 0, 42006, 1);
		addHoverButton(42007, "", 0, 85, 20, "Learn", 0, 42008, 1);
		addHoverButton(42009, "", 0, 85, 20, "Assignment", 0, 42010, 1);
		addText(42011, "Slayer Points: ", tda, 3, 0xFF981F);
		addTextButton(42012, "Cancel Task",
				"Temporarily cancel your current slayer task", 0xFF981F, false,
				true, tda, 1, 300);
		addTextButton(42013, "Remove Task permanently",
				"Permanently remove this monster as a task", 0xFF981F, false,
				true, tda, 1, 305);
		addText(42014, "line 1", tda, 1, 0xFF981F);
		addText(42015, "line 2", tda, 1, 0xFF981F);
		addText(42016, "line 3", tda, 1, 0xFF981F);
		addText(42017, "line 4", tda, 1, 0xFF981F);
		addButton(42018, 6, "Interfaces/SlayerInterface/IMAGE",
				"Delete removed slayer task");
		addButton(42019, 6, "Interfaces/SlayerInterface/IMAGE",
				"Delete removed slayer task");
		addButton(42020, 6, "Interfaces/SlayerInterface/IMAGE",
				"Delete removed slayer task");
		addButton(42021, 6, "Interfaces/SlayerInterface/IMAGE",
				"Delete removed slayer task");
		setChildren(17, rsInterface);
		rsInterface.child(0, 42001, 12, 10);
		rsInterface.child(1, 42002, 473, 20);
		rsInterface.child(2, 42003, 473, 20);
		rsInterface.child(3, 42005, 21, 23);
		rsInterface.child(4, 42007, 107, 23);
		rsInterface.child(5, 42009, 193, 23);
		rsInterface.child(6, 42011, 98, 74);
		rsInterface.child(7, 42012, 71, 127);
		rsInterface.child(8, 42013, 71, 146);
		rsInterface.child(9, 42014, 71, 216);
		rsInterface.child(10, 42015, 71, 234);
		rsInterface.child(11, 42016, 71, 252);
		rsInterface.child(12, 42017, 71, 270);
		rsInterface.child(13, 42018, 303, 215);
		rsInterface.child(14, 42019, 303, 233);
		rsInterface.child(15, 42020, 303, 251);
		rsInterface.child(16, 42021, 303, 269);
	}

	public static void homeTeleport() {
		RSInterface RSInterface = addInterface(30000);
		RSInterface.tooltip = "Cast @gre@Lunar Home Teleport";
		RSInterface.id = 30000;
		RSInterface.parentID = 30000;
		RSInterface.type = 5;
		RSInterface.atActionType = 5;
		RSInterface.contentType = 0;
		RSInterface.aByte254 = 0;
		RSInterface.mOverInterToTrigger = 30001;
		RSInterface.sprite1 = imageLoader(1, "Lunar/SPRITE");
		RSInterface.width = 20;
		RSInterface.height = 20;
		RSInterface Int = addInterface(30001);
		Int.isMouseoverTriggered = true;
		Int.mOverInterToTrigger = -1;
		setChildren(1, Int);
		addLunarSprite(30002, 0, "SPRITE");
		setBounds(30002, 0, 0, 0, Int);
	}

	public static void addLunarSprite(int i, int j, String name) {
		RSInterface RSInterface = addInterface(i);
		RSInterface.id = i;
		RSInterface.parentID = i;
		RSInterface.type = 5;
		RSInterface.atActionType = 5;
		RSInterface.contentType = 0;
		RSInterface.aByte254 = 0;
		RSInterface.mOverInterToTrigger = 52;
		RSInterface.sprite1 = LoadLunarSprite(j, name);
		RSInterface.width = 500;
		RSInterface.height = 500;
		RSInterface.tooltip = "";
	}

	public static void drawRune(int i, int id, String runeName) {
		RSInterface RSInterface = addInterface(i);
		RSInterface.type = 5;
		RSInterface.atActionType = 0;
		RSInterface.contentType = 0;
		RSInterface.aByte254 = 0;
		RSInterface.mOverInterToTrigger = 52;
		RSInterface.sprite1 = LoadLunarSprite(id, "RUNE");
		RSInterface.width = 500;
		RSInterface.height = 500;
	}

	public static void addButtons(int id, int sid, String spriteName,
			String tooltip, int mOver, int atAction) {
		RSInterface rsinterface = interfaceCache[id] = new RSInterface();
		rsinterface.id = id;
		rsinterface.parentID = id;
		rsinterface.type = 5;
		rsinterface.atActionType = atAction;
		rsinterface.contentType = 0;
		rsinterface.aByte254 = (byte) 0;
		rsinterface.mOverInterToTrigger = mOver;
		rsinterface.sprite1 = imageLoader(sid, spriteName);
		rsinterface.sprite2 = imageLoader(sid, spriteName);
		rsinterface.width = rsinterface.sprite1.myWidth;
		rsinterface.height = rsinterface.sprite2.myHeight;
		rsinterface.tooltip = tooltip;
		rsinterface.inventoryhover = true;
	}

	public static void addLunar2RunesSmallBox(int ID, int r1, int r2, int ra1,
			int ra2, int rune1, int lvl, String name, String descr,
			TextDrawingArea[] TDA, int sid, int suo, int type) {
		RSInterface rsInterface = addInterface(ID);
		rsInterface.id = ID;
		rsInterface.parentID = 1151;
		rsInterface.type = 5;
		rsInterface.atActionType = type;
		rsInterface.contentType = 0;
		rsInterface.mOverInterToTrigger = ID + 1;
		rsInterface.spellUsableOn = suo;
		rsInterface.selectedActionName = "Cast On";
		rsInterface.width = 20;
		rsInterface.height = 20;
		rsInterface.tooltip = "Cast @gre@" + name;
		rsInterface.spellName = name;
		rsInterface.anIntArray245 = new int[3];
		rsInterface.anIntArray212 = new int[3];
		rsInterface.anIntArray245[0] = 3;
		rsInterface.anIntArray212[0] = ra1;
		rsInterface.anIntArray245[1] = 3;
		rsInterface.anIntArray212[1] = ra2;
		rsInterface.anIntArray245[2] = 3;
		rsInterface.anIntArray212[2] = lvl;
		rsInterface.valueIndexArray = new int[3][];
		rsInterface.valueIndexArray[0] = new int[4];
		rsInterface.valueIndexArray[0][0] = 4;
		rsInterface.valueIndexArray[0][1] = 3214;
		rsInterface.valueIndexArray[0][2] = r1;
		rsInterface.valueIndexArray[0][3] = 0;
		rsInterface.valueIndexArray[1] = new int[4];
		rsInterface.valueIndexArray[1][0] = 4;
		rsInterface.valueIndexArray[1][1] = 3214;
		rsInterface.valueIndexArray[1][2] = r2;
		rsInterface.valueIndexArray[1][3] = 0;
		rsInterface.valueIndexArray[2] = new int[3];
		rsInterface.valueIndexArray[2][0] = 1;
		rsInterface.valueIndexArray[2][1] = 6;
		rsInterface.valueIndexArray[2][2] = 0;
		rsInterface.sprite2 = imageLoader(sid, "Lunar/LUNARON");
		rsInterface.sprite1 = imageLoader(sid, "Lunar/LUNAROFF");
		RSInterface INT = addInterface(ID + 1);
		INT.isMouseoverTriggered = true;
		INT.mOverInterToTrigger = -1;
		// setChildren(7, INT);
		// addLunarSprite(ID+2, 0, "BOX");
		// setBounds(ID+2, 0, 0, 0, INT);
		// addText(ID+3, "Level "+(lvl+1)+": "+name, 0xFF981F, true, true, 52,
		// TDA, 1);
		// setBounds(ID+3, 90, 4, 1, INT);
		// addText(ID+4, descr, 0xAF6A1A, true, true, 52, TDA, 0);
		// setBounds(ID+4, 90, 19, 2, INT);
		// setBounds(30016, 37, 35, 3, INT);//Rune
		// setBounds(rune1, 112, 35, 4, INT);//Rune
		// addRuneText(ID+5, ra1+1, r1, TDA);
		// setBounds(ID+5, 50, 66, 5, INT);
		// addRuneText(ID+6, ra2+1, r2, TDA);
		// setBounds(ID+6, 123, 66, 6, INT);

	}

	public static void addRuneText(int ID, int runeAmount, int RuneID,
			TextDrawingArea[] font) {
		RSInterface rsInterface = addInterface(ID);
		rsInterface.id = ID;
		rsInterface.parentID = 1151;
		rsInterface.type = 4;
		rsInterface.atActionType = 0;
		rsInterface.contentType = 0;
		rsInterface.width = 0;
		rsInterface.height = 14;
		rsInterface.aByte254 = 0;
		rsInterface.mOverInterToTrigger = -1;
		rsInterface.anIntArray245 = new int[1];
		rsInterface.anIntArray212 = new int[1];
		rsInterface.anIntArray245[0] = 3;
		rsInterface.anIntArray212[0] = runeAmount;
		rsInterface.valueIndexArray = new int[1][4];
		rsInterface.valueIndexArray[0][0] = 4;
		rsInterface.valueIndexArray[0][1] = 3214;
		rsInterface.valueIndexArray[0][2] = RuneID;
		rsInterface.valueIndexArray[0][3] = 0;
		rsInterface.centerText = true;
		rsInterface.textDrawingAreas = font[0];
		// rsInterface.textShadowed = true;
		rsInterface.message = "%1/" + runeAmount + "";
		rsInterface.message = "";
		// rsInterface.disabledColour = 12582912;
		// rsInterface.enabledColour = 49152;
	}

	public static void addLunar3RunesSmallBox(int ID, int r1, int r2, int r3,
			int ra1, int ra2, int ra3, int rune1, int rune2, int lvl,
			String name, String descr, TextDrawingArea[] TDA, int sid, int suo,
			int type) {
		RSInterface rsInterface = addInterface(ID);
		rsInterface.id = ID;
		rsInterface.parentID = 1151;
		rsInterface.type = 5;
		rsInterface.atActionType = type;
		rsInterface.contentType = 0;
		rsInterface.mOverInterToTrigger = ID + 1;
		rsInterface.spellUsableOn = suo;
		rsInterface.selectedActionName = "Cast on";
		rsInterface.width = 20;
		rsInterface.height = 20;
		rsInterface.tooltip = "Cast @gre@" + name;
		rsInterface.spellName = name;
		rsInterface.anIntArray245 = new int[4];
		rsInterface.anIntArray212 = new int[4];
		rsInterface.anIntArray245[0] = 3;
		rsInterface.anIntArray212[0] = ra1;
		rsInterface.anIntArray245[1] = 3;
		rsInterface.anIntArray212[1] = ra2;
		rsInterface.anIntArray245[2] = 3;
		rsInterface.anIntArray212[2] = ra3;
		rsInterface.anIntArray245[3] = 3;
		rsInterface.anIntArray212[3] = lvl;
		rsInterface.valueIndexArray = new int[4][];
		rsInterface.valueIndexArray[0] = new int[4];
		rsInterface.valueIndexArray[0][0] = 4;
		rsInterface.valueIndexArray[0][1] = 3214;
		rsInterface.valueIndexArray[0][2] = r1;
		rsInterface.valueIndexArray[0][3] = 0;
		rsInterface.valueIndexArray[1] = new int[4];
		rsInterface.valueIndexArray[1][0] = 4;
		rsInterface.valueIndexArray[1][1] = 3214;
		rsInterface.valueIndexArray[1][2] = r2;
		rsInterface.valueIndexArray[1][3] = 0;
		rsInterface.valueIndexArray[2] = new int[4];
		rsInterface.valueIndexArray[2][0] = 4;
		rsInterface.valueIndexArray[2][1] = 3214;
		rsInterface.valueIndexArray[2][2] = r3;
		rsInterface.valueIndexArray[2][3] = 0;
		rsInterface.valueIndexArray[3] = new int[3];
		rsInterface.valueIndexArray[3][0] = 1;
		rsInterface.valueIndexArray[3][1] = 6;
		rsInterface.valueIndexArray[3][2] = 0;
		rsInterface.sprite2 = imageLoader(sid, "Lunar/LUNARON");
		rsInterface.sprite1 = imageLoader(sid, "Lunar/LUNAROFF");
		RSInterface INT = addInterface(ID + 1);
		INT.isMouseoverTriggered = true;
		INT.mOverInterToTrigger = -1;
		// setChildren(9, INT);
		// addLunarSprite(ID+2, 0, "BOX");
		// setBounds(ID+2, 0, 0, 0, INT);
		// addText(ID+3, "Level "+(lvl+1)+": "+name, 0xFF981F, true, true, 52,
		// TDA, 1);setBounds(ID+3, 90, 4, 1, INT);
		// addText(ID+4, descr, 0xAF6A1A, true, true, 52, TDA, 0);
		// setBounds(ID+4, 90, 19, 2, INT);
		// setBounds(30016, 14, 35, 3, INT);
		// setBounds(rune1, 74, 35, 4, INT);
		// setBounds(rune2, 130, 35, 5, INT);
		// addRuneText(ID+5, ra1+1, r1, TDA);
		// setBounds(ID+5, 26, 66, 6, INT);
		// addRuneText(ID+6, ra2+1, r2, TDA);
		// setBounds(ID+6, 87, 66, 7, INT);
		// addRuneText(ID+7, ra3+1, r3, TDA);
		// setBounds(ID+7, 142, 66, 8, INT);
	}

	public static void addLunar3RunesBigBox(int ID, int r1, int r2, int r3,
			int ra1, int ra2, int ra3, int rune1, int rune2, int lvl,
			String name, String descr, TextDrawingArea[] TDA, int sid, int suo,
			int type) {
		RSInterface rsInterface = addInterface(ID);
		rsInterface.id = ID;
		rsInterface.parentID = 1151;
		rsInterface.type = 5;
		rsInterface.atActionType = type;
		rsInterface.contentType = 0;
		rsInterface.mOverInterToTrigger = ID + 1;
		rsInterface.spellUsableOn = suo;
		rsInterface.selectedActionName = "Cast on";
		rsInterface.width = 20;
		rsInterface.height = 20;
		rsInterface.tooltip = "Cast @gre@" + name;
		rsInterface.spellName = name;
		rsInterface.anIntArray245 = new int[4];
		rsInterface.anIntArray212 = new int[4];
		rsInterface.anIntArray245[0] = 3;
		rsInterface.anIntArray212[0] = ra1;
		rsInterface.anIntArray245[1] = 3;
		rsInterface.anIntArray212[1] = ra2;
		rsInterface.anIntArray245[2] = 3;
		rsInterface.anIntArray212[2] = ra3;
		rsInterface.anIntArray245[3] = 3;
		rsInterface.anIntArray212[3] = lvl;
		rsInterface.valueIndexArray = new int[4][];
		rsInterface.valueIndexArray[0] = new int[4];
		rsInterface.valueIndexArray[0][0] = 4;
		rsInterface.valueIndexArray[0][1] = 3214;
		rsInterface.valueIndexArray[0][2] = r1;
		rsInterface.valueIndexArray[0][3] = 0;
		rsInterface.valueIndexArray[1] = new int[4];
		rsInterface.valueIndexArray[1][0] = 4;
		rsInterface.valueIndexArray[1][1] = 3214;
		rsInterface.valueIndexArray[1][2] = r2;
		rsInterface.valueIndexArray[1][3] = 0;
		rsInterface.valueIndexArray[2] = new int[4];
		rsInterface.valueIndexArray[2][0] = 4;
		rsInterface.valueIndexArray[2][1] = 3214;
		rsInterface.valueIndexArray[2][2] = r3;
		rsInterface.valueIndexArray[2][3] = 0;
		rsInterface.valueIndexArray[3] = new int[3];
		rsInterface.valueIndexArray[3][0] = 1;
		rsInterface.valueIndexArray[3][1] = 6;
		rsInterface.valueIndexArray[3][2] = 0;
		rsInterface.sprite2 = imageLoader(sid, "Lunar/LUNARON");
		rsInterface.sprite1 = imageLoader(sid, "Lunar/LUNAROFF");
		RSInterface INT = addInterface(ID + 1);
		INT.isMouseoverTriggered = true;
		INT.mOverInterToTrigger = -1;
		// setChildren(9, INT);
		// addLunarSprite(ID+2, 1, "BOX");
		// setBounds(ID+2, 0, 0, 0, INT);
		// addText(ID+3, "Level "+(lvl+1)+": "+name, 0xFF981F, true, true, 52,
		// TDA, 1);setBounds(ID+3, 90, 4, 1, INT);
		// addText(ID+4, descr, 0xAF6A1A, true, true, 52, TDA, 0);
		// setBounds(ID+4, 90, 21, 2, INT);
		// setBounds(30016, 14, 48, 3, INT);
		// setBounds(rune1, 74, 48, 4, INT);
		// setBounds(rune2, 130, 48, 5, INT);
		// addRuneText(ID+5, ra1+1, r1, TDA);
		// setBounds(ID+5, 26, 79, 6, INT);
		// addRuneText(ID+6, ra2+1, r2, TDA);
		// setBounds(ID+6, 87, 79, 7, INT);
		// addRuneText(ID+7, ra3+1, r3, TDA);
		// setBounds(ID+7, 142, 79, 8, INT);
	}

	public static void addLunar3RunesLargeBox(int ID, int r1, int r2, int r3,
			int ra1, int ra2, int ra3, int rune1, int rune2, int lvl,
			String name, String descr, TextDrawingArea[] TDA, int sid, int suo,
			int type) {
		RSInterface rsInterface = addInterface(ID);
		rsInterface.id = ID;
		rsInterface.parentID = 1151;
		rsInterface.type = 5;
		rsInterface.atActionType = type;
		rsInterface.contentType = 0;
		rsInterface.mOverInterToTrigger = ID + 1;
		rsInterface.spellUsableOn = suo;
		rsInterface.selectedActionName = "Cast on";
		rsInterface.width = 20;
		rsInterface.height = 20;
		rsInterface.tooltip = "Cast @gre@" + name;
		rsInterface.spellName = name;
		rsInterface.anIntArray245 = new int[4];
		rsInterface.anIntArray212 = new int[4];
		rsInterface.anIntArray245[0] = 3;
		rsInterface.anIntArray212[0] = ra1;
		rsInterface.anIntArray245[1] = 3;
		rsInterface.anIntArray212[1] = ra2;
		rsInterface.anIntArray245[2] = 3;
		rsInterface.anIntArray212[2] = ra3;
		rsInterface.anIntArray245[3] = 3;
		rsInterface.anIntArray212[3] = lvl;
		rsInterface.valueIndexArray = new int[4][];
		rsInterface.valueIndexArray[0] = new int[4];
		rsInterface.valueIndexArray[0][0] = 4;
		rsInterface.valueIndexArray[0][1] = 3214;
		rsInterface.valueIndexArray[0][2] = r1;
		rsInterface.valueIndexArray[0][3] = 0;
		rsInterface.valueIndexArray[1] = new int[4];
		rsInterface.valueIndexArray[1][0] = 4;
		rsInterface.valueIndexArray[1][1] = 3214;
		rsInterface.valueIndexArray[1][2] = r2;
		rsInterface.valueIndexArray[1][3] = 0;
		rsInterface.valueIndexArray[2] = new int[4];
		rsInterface.valueIndexArray[2][0] = 4;
		rsInterface.valueIndexArray[2][1] = 3214;
		rsInterface.valueIndexArray[2][2] = r3;
		rsInterface.valueIndexArray[2][3] = 0;
		rsInterface.valueIndexArray[3] = new int[3];
		rsInterface.valueIndexArray[3][0] = 1;
		rsInterface.valueIndexArray[3][1] = 6;
		rsInterface.valueIndexArray[3][2] = 0;
		rsInterface.sprite2 = imageLoader(sid, "Lunar/LUNARON");
		rsInterface.sprite1 = imageLoader(sid, "Lunar/LUNAROFF");
		RSInterface INT = addInterface(ID + 1);
		INT.isMouseoverTriggered = true;
		INT.mOverInterToTrigger = -1;
		// setChildren(9, INT);
		// addLunarSprite(ID+2, 2, "BOX");
		// setBounds(ID+2, 0, 0, 0, INT);
		// addText(ID+3, "Level "+(lvl+1)+": "+name, 0xFF981F, true, true, 52,
		// TDA, 1);
		// setBounds(ID+3, 90, 4, 1, INT);
		// addText(ID+4, descr, 0xAF6A1A, true, true, 52, TDA, 0);
		// setBounds(ID+4, 90, 34, 2, INT);
		// setBounds(30016, 14, 61, 3, INT);
		// setBounds(rune1, 74, 61, 4, INT);
		// setBounds(rune2, 130, 61, 5, INT);
		// addRuneText(ID+5, ra1+1, r1, TDA);
		// setBounds(ID+5, 26, 92, 6, INT);
		// addRuneText(ID+6, ra2+1, r2, TDA);
		// setBounds(ID+6, 87, 92, 7, INT);
		// addRuneText(ID+7, ra3+1, r3, TDA);
		// setBounds(ID+7, 142, 92, 8, INT);
	}

	private static Sprite LoadLunarSprite(int i, String s) {
		Sprite sprite = imageLoader(i, "/Lunar/" + s);
		return sprite;
	}

	public static void Levelup(TextDrawingArea[] tda) {
		RSInterface text = interfaceCache[7202];
		RSInterface attack = interfaceCache[6247];
		RSInterface defence = interfaceCache[6253];
		RSInterface str = interfaceCache[6206];
		RSInterface hits = interfaceCache[6216];
		RSInterface rng = interfaceCache[4443];
		RSInterface pray = interfaceCache[6242];
		RSInterface mage = interfaceCache[6211];
		RSInterface cook = interfaceCache[6226];
		RSInterface wood = interfaceCache[4272];
		RSInterface flet = interfaceCache[6231];
		RSInterface fish = interfaceCache[6258];
		RSInterface fire = interfaceCache[4282];
		RSInterface craf = interfaceCache[6263];
		RSInterface smit = interfaceCache[6221];
		RSInterface mine = interfaceCache[4416];
		RSInterface herb = interfaceCache[6237];
		RSInterface agil = interfaceCache[4277];
		RSInterface thie = interfaceCache[4261];
		RSInterface slay = interfaceCache[12122];
		RSInterface farm = interfaceCache[5267];
		RSInterface rune = interfaceCache[4267];
		RSInterface cons = interfaceCache[7267];
		RSInterface hunt = interfaceCache[8267];
		RSInterface summ = addInterface(9267);
		RSInterface dung = addInterface(10267);
		addSprite(17878, 0, "Interfaces/skillchat/skill");
		addSprite(17879, 1, "Interfaces/skillchat/skill");
		addSprite(17880, 2, "Interfaces/skillchat/skill");
		addSprite(17881, 3, "Interfaces/skillchat/skill");
		addSprite(17882, 4, "Interfaces/skillchat/skill");
		addSprite(17883, 5, "Interfaces/skillchat/skill");
		addSprite(17884, 6, "Interfaces/skillchat/skill");
		addSprite(17885, 7, "Interfaces/skillchat/skill");
		addSprite(17886, 8, "Interfaces/skillchat/skill");
		addSprite(17887, 9, "Interfaces/skillchat/skill");
		addSprite(17888, 10, "Interfaces/skillchat/skill");
		addSprite(17889, 11, "Interfaces/skillchat/skill");
		addSprite(17890, 12, "Interfaces/skillchat/skill");
		addSprite(17891, 13, "Interfaces/skillchat/skill");
		addSprite(17892, 14, "Interfaces/skillchat/skill");
		addSprite(17893, 15, "Interfaces/skillchat/skill");
		addSprite(17894, 16, "Interfaces/skillchat/skill");
		addSprite(17895, 17, "Interfaces/skillchat/skill");
		addSprite(17896, 18, "Interfaces/skillchat/skill");
		addSprite(11897, 19, "Interfaces/skillchat/skill");
		addSprite(17898, 20, "Interfaces/skillchat/skill");
		addSprite(17899, 21, "Interfaces/skillchat/skill");
		addSprite(17900, 22, "Interfaces/skillchat/skill");
		addSprite(17901, 23, "Interfaces/skillchat/skill");
		addSprite(17902, 24, "Interfaces/skillchat/skill");
		setChildren(4, attack);
		setBounds(17878, 20, 30, 0, attack);
		setBounds(4268, 80, 15, 1, attack);
		setBounds(4269, 80, 45, 2, attack);
		setBounds(358, 95, 75, 3, attack);
		setChildren(4, defence);
		setBounds(17879, 20, 30, 0, defence);
		setBounds(4268, 80, 15, 1, defence);
		setBounds(4269, 80, 45, 2, defence);
		setBounds(358, 95, 75, 3, defence);
		setChildren(4, str);
		setBounds(17880, 20, 30, 0, str);
		setBounds(4268, 80, 15, 1, str);
		setBounds(4269, 80, 45, 2, str);
		setBounds(358, 95, 75, 3, str);
		setChildren(4, hits);
		setBounds(17881, 20, 30, 0, hits);
		setBounds(4268, 80, 15, 1, hits);
		setBounds(4269, 80, 45, 2, hits);
		setBounds(358, 95, 75, 3, hits);
		setChildren(4, rng);
		setBounds(17882, 20, 30, 0, rng);
		setBounds(4268, 80, 15, 1, rng);
		setBounds(4269, 80, 45, 2, rng);
		setBounds(358, 95, 75, 3, rng);
		setChildren(4, pray);
		setBounds(17883, 20, 30, 0, pray);
		setBounds(4268, 80, 15, 1, pray);
		setBounds(4269, 80, 45, 2, pray);
		setBounds(358, 95, 75, 3, pray);
		setChildren(4, mage);
		setBounds(17884, 20, 30, 0, mage);
		setBounds(4268, 80, 15, 1, mage);
		setBounds(4269, 80, 45, 2, mage);
		setBounds(358, 95, 75, 3, mage);
		setChildren(4, cook);
		setBounds(17885, 20, 30, 0, cook);
		setBounds(4268, 80, 15, 1, cook);
		setBounds(4269, 80, 45, 2, cook);
		setBounds(358, 95, 75, 3, cook);
		setChildren(4, wood);
		setBounds(17886, 20, 30, 0, wood);
		setBounds(4268, 80, 15, 1, wood);
		setBounds(4269, 80, 45, 2, wood);
		setBounds(358, 95, 75, 3, wood);
		setChildren(4, flet);
		setBounds(17887, 20, 30, 0, flet);
		setBounds(4268, 80, 15, 1, flet);
		setBounds(4269, 80, 45, 2, flet);
		setBounds(358, 95, 75, 3, flet);
		setChildren(4, fish);
		setBounds(17888, 20, 30, 0, fish);
		setBounds(4268, 80, 15, 1, fish);
		setBounds(4269, 80, 45, 2, fish);
		setBounds(358, 95, 75, 3, fish);
		setChildren(4, fire);
		setBounds(17889, 20, 30, 0, fire);
		setBounds(4268, 80, 15, 1, fire);
		setBounds(4269, 80, 45, 2, fire);
		setBounds(358, 95, 75, 3, fire);
		setChildren(4, craf);
		setBounds(17890, 20, 30, 0, craf);
		setBounds(4268, 80, 15, 1, craf);
		setBounds(4269, 80, 45, 2, craf);
		setBounds(358, 95, 75, 3, craf);
		setChildren(4, smit);
		setBounds(17891, 20, 30, 0, smit);
		setBounds(4268, 80, 15, 1, smit);
		setBounds(4269, 80, 45, 2, smit);
		setBounds(358, 95, 75, 3, smit);
		setChildren(4, mine);
		setBounds(17892, 20, 30, 0, mine);
		setBounds(4268, 80, 15, 1, mine);
		setBounds(4269, 80, 45, 2, mine);
		setBounds(358, 95, 75, 3, mine);
		setChildren(4, herb);
		setBounds(17893, 20, 30, 0, herb);
		setBounds(4268, 80, 15, 1, herb);
		setBounds(4269, 80, 45, 2, herb);
		setBounds(358, 95, 75, 3, herb);
		setChildren(4, agil);
		setBounds(17894, 20, 30, 0, agil);
		setBounds(4268, 80, 15, 1, agil);
		setBounds(4269, 80, 45, 2, agil);
		setBounds(358, 95, 75, 3, agil);
		setChildren(4, thie);
		setBounds(17895, 20, 30, 0, thie);
		setBounds(4268, 80, 15, 1, thie);
		setBounds(4269, 80, 45, 2, thie);
		setBounds(358, 95, 75, 3, thie);
		setChildren(4, slay);
		setBounds(17896, 20, 30, 0, slay);
		setBounds(4268, 80, 15, 1, slay);
		setBounds(4269, 80, 45, 2, slay);
		setBounds(358, 95, 75, 3, slay);
		setChildren(3, farm);
		setBounds(4268, 80, 15, 0, farm);
		setBounds(4269, 80, 45, 1, farm);
		setBounds(358, 95, 75, 2, farm);
		setChildren(4, rune);
		setBounds(17898, 20, 30, 0, rune);
		setBounds(4268, 80, 15, 1, rune);
		setBounds(4269, 80, 45, 2, rune);
		setBounds(358, 95, 75, 3, rune);
		setChildren(3, cons);
		setBounds(4268, 80, 15, 0, cons);
		setBounds(4269, 80, 45, 1, cons);
		setBounds(358, 95, 75, 2, cons);
		setChildren(3, hunt);
		setBounds(4268, 80, 15, 0, hunt);
		setBounds(4269, 80, 45, 1, hunt);
		setBounds(358, 95, 75, 2, hunt);
		setChildren(4, summ);
		setBounds(17901, 20, 30, 0, summ);
		setBounds(4268, 80, 15, 1, summ);
		setBounds(4269, 80, 45, 2, summ);
		setBounds(358, 95, 75, 3, summ);
		setChildren(4, dung);
		setBounds(17902, 20, 30, 0, dung);
		setBounds(4268, 80, 15, 1, dung);
		setBounds(4269, 80, 45, 2, dung);
		setBounds(358, 95, 75, 3, dung);
	}

	public static void addActionButton(int id, int sprite, int sprite2,
			int width, int height, String s) {
		RSInterface rsi = interfaceCache[id] = new RSInterface();
		rsi.sprite1 = CustomSpriteLoader(sprite, "");
		if (sprite2 == sprite)
			rsi.sprite2 = CustomSpriteLoader(sprite, "a");
		else
			rsi.sprite2 = CustomSpriteLoader(sprite2, "");
		rsi.tooltip = s;
		rsi.contentType = 0;
		rsi.atActionType = 1;
		rsi.width = width;
		rsi.mOverInterToTrigger = 52;
		rsi.parentID = id;
		rsi.id = id;
		rsi.type = 5;
		rsi.height = height;
	}

	public static void addButton(int id, int sid, String spriteName,
			String tooltip, int mOver, int atAction, int width, int height) {
		RSInterface tab = interfaceCache[id] = new RSInterface();
		tab.id = id;
		tab.parentID = id;
		tab.type = 5;
		tab.atActionType = atAction;
		tab.contentType = 0;
		tab.aByte254 = 0;
		tab.mOverInterToTrigger = mOver;
		tab.sprite1 = imageLoader(sid, spriteName);
		tab.sprite2 = imageLoader(sid, spriteName);
		tab.width = width;
		tab.height = height;
		tab.tooltip = tooltip;
		tab.inventoryhover = true;
	}

	public static void addText(int id, String text, TextDrawingArea wid[],
			int idx, int color) {
		RSInterface rsinterface = addTab(id);
		rsinterface.id = id;
		rsinterface.parentID = id;
		rsinterface.type = 4;
		rsinterface.atActionType = 0;
		rsinterface.width = 174;
		rsinterface.height = 11;
		rsinterface.contentType = 0;
		rsinterface.aByte254 = 0;
		rsinterface.mOverInterToTrigger = -1;
		rsinterface.centerText = false;
		rsinterface.textShadow = true;
		rsinterface.textDrawingAreas = wid[idx];
		rsinterface.message = text;
		rsinterface.aString228 = "";
		rsinterface.textColor = color;
		rsinterface.anInt219 = 0;
		rsinterface.anInt216 = 0;
		rsinterface.anInt239 = 0;
	}

	public static void sprite1(int id, int sprite) {
		RSInterface class9 = interfaceCache[id];
		class9.sprite1 = CustomSpriteLoader(sprite, "");
	}

	public static RSInterface addInterface(int id) {
		RSInterface rsi = interfaceCache[id] = new RSInterface();
		rsi.id = id;
		rsi.parentID = id;
		rsi.width = 512;
		rsi.height = 334;
		return rsi;
	}

	public static void addHoverText(int id, String text, String tooltip,
			TextDrawingArea tda[], int idx, int color, boolean center,
			boolean textShadowed, int width) {
		RSInterface rsinterface = addInterface(id);
		rsinterface.id = id;
		rsinterface.parentID = id;
		rsinterface.type = 4;
		rsinterface.atActionType = 1;
		rsinterface.width = width;
		rsinterface.height = 11;
		rsinterface.contentType = 0;
		rsinterface.aByte254 = 0;
		rsinterface.mOverInterToTrigger = -1;
		rsinterface.centerText = center;
		rsinterface.textShadow = textShadowed;
		rsinterface.textDrawingAreas = tda[idx];
		rsinterface.message = text;
		rsinterface.aString228 = "";
		rsinterface.textColor = color;
		rsinterface.anInt219 = 0;
		rsinterface.anInt216 = 0xffffff;
		rsinterface.anInt239 = 0;
		rsinterface.tooltip = tooltip;
	}

	public static RSInterface addTab(int i) {
		RSInterface rsinterface = interfaceCache[i] = new RSInterface();
		rsinterface.id = i;
		rsinterface.parentID = i;
		rsinterface.type = 0;
		rsinterface.atActionType = 0;
		rsinterface.contentType = 0;
		rsinterface.width = 512;
		rsinterface.height = 334;
		rsinterface.aByte254 = 0;
		rsinterface.mOverInterToTrigger = 0;
		return rsinterface;
	}

	public static void addConfigButton(int ID, int pID, int bID, int bID2,
			String bName, int width, int height, String tT, int configID,
			int aT, int configFrame) {
		RSInterface Tab = addTabInterface(ID);
		Tab.parentID = pID;
		Tab.id = ID;
		Tab.type = 5;
		Tab.atActionType = aT;
		Tab.contentType = 0;
		Tab.width = width;
		Tab.height = height;
		Tab.aByte254 = 0;
		Tab.mOverInterToTrigger = -1;
		Tab.anIntArray245 = new int[1];
		Tab.anIntArray212 = new int[1];
		Tab.anIntArray245[0] = 1;
		Tab.anIntArray212[0] = configID;
		Tab.valueIndexArray = new int[1][3];
		Tab.valueIndexArray[0][0] = 5;
		Tab.valueIndexArray[0][1] = configFrame;
		Tab.valueIndexArray[0][2] = 0;
		Tab.sprite1 = imageLoader(bID, bName);
		Tab.sprite2 = imageLoader(bID2, bName);
		Tab.tooltip = tT;
	}

	public static void drawBlackBox(int xPos, int yPos) {
		// /Light Coloured Borders\\\
		DrawingArea.drawPixels(71, yPos - 1, xPos - 2, 0x726451, 1); // Left
																		// line
		DrawingArea.drawPixels(69, yPos, xPos + 174, 0x726451, 1); // Right line
		DrawingArea.drawPixels(1, yPos - 2, xPos - 2, 0x726451, 178); // Top
																		// Line
		DrawingArea.drawPixels(1, yPos + 68, xPos, 0x726451, 174); // Bottom
																	// Line

		// /Dark Coloured Borders\\\
		DrawingArea.drawPixels(71, yPos - 1, xPos - 1, 0x2E2B23, 1); // Left
																		// line
		DrawingArea.drawPixels(71, yPos - 1, xPos + 175, 0x2E2B23, 1); // Right
																		// line
		DrawingArea.drawPixels(1, yPos - 1, xPos, 0x2E2B23, 175); // Top line
		DrawingArea.drawPixels(1, yPos + 69, xPos, 0x2E2B23, 175); // Top line

		// /Black Box\\\
		DrawingArea.method335(0x000000, yPos, 174, 68, 220, xPos); // Yes
																	// method335
																	// is
																	// galkons
																	// opacity
																	// method
	}

	public Sprite disabledHover;
	public Sprite enabledHover;

	public static void addPrayer(int i, int configId, int configFrame,
			int requiredValues, int spriteID, String prayerName) {
		RSInterface tab = addTabInterface(i);
		tab.id = i;
		tab.parentID = 5608;
		tab.type = 5;
		tab.atActionType = 4;
		tab.contentType = 0;
		tab.aByte254 = 0;
		tab.mOverInterToTrigger = -1;
		tab.sprite1 = imageLoader(0, "PRAYERGLOW");
		tab.sprite2 = imageLoader(1, "PRAYERGLOW");
		tab.width = 34;
		tab.height = 34;
		tab.anIntArray245 = new int[1];
		tab.anIntArray212 = new int[1];
		tab.anIntArray245[0] = 1;
		tab.anIntArray212[0] = configId;
		tab.valueIndexArray = new int[1][3];
		tab.valueIndexArray[0][0] = 5;
		tab.valueIndexArray[0][1] = configFrame;
		tab.valueIndexArray[0][2] = 0;
		// tab.tooltip = "Activate@or2@ " + prayerName;
		tab.tooltip = "Select";
		RSInterface tab2 = addTabInterface(i + 1);
		tab2.id = i + 1;
		tab2.parentID = 5608;
		tab2.type = 5;
		tab2.atActionType = 0;
		tab2.contentType = 0;
		tab2.aByte254 = 0;
		tab2.mOverInterToTrigger = -1;
		tab2.sprite1 = imageLoader(spriteID, "Prayer/PRAYON");
		tab2.sprite2 = imageLoader(spriteID, "Prayer/PRAYOFF");
		tab2.width = 34;
		tab2.height = 34;
		tab2.anIntArray245 = new int[1];
		tab2.anIntArray212 = new int[1];
		tab2.anIntArray245[0] = 2;
		tab2.anIntArray212[0] = requiredValues + 1;
		tab2.valueIndexArray = new int[1][3];
		tab2.valueIndexArray[0][0] = 2;
		tab2.valueIndexArray[0][1] = 5;
		tab2.valueIndexArray[0][2] = 0;
	}

	public static void addToggleButton(int id, int sprite, int setconfig,
			int width, int height, String s) {
		RSInterface rsi = addInterface(id);
		rsi.sprite1 = CustomSpriteLoader(sprite, "");
		rsi.sprite2 = CustomSpriteLoader(sprite, "a");
		rsi.anIntArray212 = new int[1];
		rsi.anIntArray212[0] = 1;
		rsi.anIntArray245 = new int[1];
		rsi.anIntArray245[0] = 1;
		rsi.valueIndexArray = new int[1][3];
		rsi.valueIndexArray[0][0] = 5;
		rsi.valueIndexArray[0][1] = setconfig;
		rsi.valueIndexArray[0][2] = 0;
		rsi.atActionType = 4;
		rsi.width = width;
		rsi.mOverInterToTrigger = -1;
		rsi.parentID = id;
		rsi.id = id;
		rsi.type = 5;
		rsi.height = height;
		rsi.tooltip = s;
	}

	public static void removeSomething(int id) {
		@SuppressWarnings("unused")
		RSInterface rsi = interfaceCache[id] = new RSInterface();
	}

	public static void setBounds(int ID, int X, int Y, int frame,
			RSInterface RSinterface) {
		RSinterface.children[frame] = ID;
		RSinterface.childX[frame] = X;
		RSinterface.childY[frame] = Y;
	}

	public static void textSize(int id, TextDrawingArea tda[], int idx) {
		RSInterface rsi = interfaceCache[id];
		rsi.textDrawingAreas = tda[idx];
	}

	/**
	 * Adds your current character to an interface.
	 **/

	protected static void addOldPrayer(int id, String prayerName) {
		RSInterface rsi = interfaceCache[id];
		rsi.tooltip = "Activate@or2@ " + prayerName;
	}

	public static void addPrayerHover(int i, int hoverID, int prayerSpriteID,
			String hoverText) {
		RSInterface Interface = addTabInterface(i);
		Interface.id = i;
		Interface.parentID = 5608;
		Interface.type = 5;
		Interface.atActionType = 0;
		Interface.contentType = 0;
		Interface.aByte254 = 0;
		Interface.mOverInterToTrigger = hoverID;
		Interface.sprite2 = imageLoader(0, "tabs/prayer/hover/PRAYERH");
		Interface.sprite1 = imageLoader(0, "tabs/prayer/hover/PRAYERH");
		Interface.width = 34;
		Interface.height = 34;

		Interface = addTabInterface(hoverID);
		Interface.id = hoverID;
		Interface.parentID = 5608;
		Interface.type = 0;
		Interface.atActionType = 0;
		Interface.contentType = 0;
		Interface.aByte254 = 0;
		Interface.mOverInterToTrigger = -1;
		Interface.width = 512;
		Interface.height = 334;
		Interface.isMouseoverTriggered = true;
		addBox(hoverID + 1, 0, false, 0x000000, hoverText);
		setChildren(1, Interface);
		setBounds(hoverID + 1, 0, 0, 0, Interface);
	}

	public static void addChar(int ID) {
		RSInterface t = interfaceCache[ID] = new RSInterface();
		t.id = ID;
		t.parentID = ID;
		t.type = 6;
		t.atActionType = 0;
		t.contentType = 328;
		t.width = 136;
		t.height = 168;
		t.aByte254 = 0;
		t.mOverInterToTrigger = 0;
		t.modelZoom = 560;
		t.modelRotation1 = 150;
		t.modelRotation2 = 0;
		t.anInt257 = -1;
		t.anInt258 = -1;
	}

	public static void addCacheSprite(int id, int sprite1, int sprite2,
			String sprites) {
		RSInterface rsi = interfaceCache[id] = new RSInterface();
		rsi.sprite1 = method207(sprite1, aClass44, sprites);
		rsi.sprite2 = method207(sprite2, aClass44, sprites);
		rsi.parentID = id;
		rsi.id = id;
		rsi.type = 5;
	}

	public void specialBar(int id, TextDrawingArea[] tda) // 7599
	{
		addActionButton(id - 12, 7587, -1, 150, 26, "Use @gre@Special Attack");
		for (int i = id - 11; i < id; i++)
			removeSomething(i);

		RSInterface rsi = interfaceCache[id - 12];
		rsi.width = 150;
		rsi.height = 26;
		rsi.mOverInterToTrigger = 40005;

		rsi = interfaceCache[id];
		rsi.width = 150;
		rsi.height = 26;

		rsi.child(0, id - 12, 0, 0);

		rsi.child(12, id + 1, 3, 7);

		rsi.child(23, id + 12, 16, 8);

		for (int i = 13; i < 23; i++) {
			rsi.childY[i] -= 1;
		}

		rsi = interfaceCache[id + 1];
		rsi.type = 5;
		rsi.sprite1 = CustomSpriteLoader(7600, "");

		for (int i = id + 2; i < id + 12; i++) {
			rsi = interfaceCache[i];
			rsi.type = 5;
		}

		sprite1(id + 2, 7601);
		sprite1(id + 3, 7602);
		sprite1(id + 4, 7603);
		sprite1(id + 5, 7604);
		sprite1(id + 6, 7605);
		sprite1(id + 7, 7606);
		sprite1(id + 8, 7607);
		sprite1(id + 9, 7608);
		sprite1(id + 10, 7609);
		sprite1(id + 11, 7610);

		rsi = addInterface(40005);
		rsi.isMouseoverTriggered = true;
		rsi.type = 0;
		rsi.atActionType = 0;
		rsi.mOverInterToTrigger = -1;
		rsi.parentID = 40005;
		rsi.id = 40005;
		addBox(40006, 0, false, 0x000000,
				"Select to perform a special\nattack.");
		setChildren(1, rsi);
		setBounds(40006, 0, 0, 0, rsi);
	}

	public static void addAttackHover(int id, int hoverID, String hoverText,
			TextDrawingArea[] TDA) {
		RSInterface rsi = interfaceCache[id];
		rsi.mOverInterToTrigger = hoverID;

		rsi = addInterface(hoverID);
		rsi.isMouseoverTriggered = true;
		rsi.type = 0;
		rsi.atActionType = 0;
		rsi.mOverInterToTrigger = -1;
		rsi.parentID = hoverID;
		rsi.id = hoverID;
		addBox(hoverID + 1, 0, false, 0x000000, hoverText);
		setChildren(1, rsi);
		setBounds(hoverID + 1, 0, 0, 0, rsi);
	}

	public static void addAttackText(int id, String text,
			TextDrawingArea tda[], int idx, int color, boolean centered) {
		RSInterface rsi = interfaceCache[id] = new RSInterface();
		if (centered)
			rsi.centerText = true;
		rsi.textShadow = true;
		rsi.textDrawingAreas = tda[idx];
		rsi.message = text;
		rsi.textColor = color;
		rsi.id = id;
		rsi.type = 4;
	}

	public static void addAttackStyleButton2(int id, int sprite, int setconfig,
			int width, int height, String s, int hoverID, int hW, int hH,
			String hoverText, TextDrawingArea[] TDA) {
		RSInterface rsi = addInterface(id);
		rsi.sprite1 = CustomSpriteLoader(sprite, "");
		rsi.sprite2 = CustomSpriteLoader(sprite, "a");
		rsi.anIntArray245 = new int[1];
		rsi.anIntArray245[0] = 1;
		rsi.anIntArray212 = new int[1];
		rsi.anIntArray212[0] = 1;
		rsi.valueIndexArray = new int[1][3];
		rsi.valueIndexArray[0][0] = 5;
		rsi.valueIndexArray[0][1] = setconfig;
		rsi.valueIndexArray[0][2] = 0;
		rsi.atActionType = 4;
		rsi.width = width;
		rsi.mOverInterToTrigger = hoverID;
		rsi.parentID = id;
		rsi.id = id;
		rsi.type = 5;
		rsi.height = height;
		rsi.tooltip = s;
		rsi = addInterface(hoverID);
		rsi.isMouseoverTriggered = true;
		rsi.type = 0;
		rsi.atActionType = 0;
		rsi.mOverInterToTrigger = -1;
		rsi.parentID = hoverID;
		rsi.id = hoverID;
		addBox(hoverID + 1, 0, false, 0x000000, hoverText);
		setChildren(1, rsi);
		setBounds(hoverID + 1, 0, 0, 0, rsi);
	}

	public static void addBox(int id, int byte1, boolean filled, int color,
			String text) {
		RSInterface Interface = addInterface(id);
		Interface.id = id;
		Interface.parentID = id;
		Interface.type = 9;
		Interface.aByte254 = (byte) byte1;
		Interface.aBoolean227 = filled;
		Interface.mOverInterToTrigger = -1;
		Interface.atActionType = 0;
		Interface.contentType = 0;
		Interface.textColor = color;
		Interface.message = text;
	}

	public static void setChildren(int total, RSInterface i) {
		i.children = new int[total];
		i.childX = new int[total];
		i.childY = new int[total];
	}

	protected static Sprite CustomSpriteLoader(int id, String s) {
		long l = (TextClass.method585(s) << 8) + id;
		Sprite sprite = (Sprite) aMRUNodes_238.insertFromCache(l);
		if (sprite != null) {
			return sprite;
		}
		try {
			sprite = new Sprite("/Attack/" + id + s);
			aMRUNodes_238.removeFromCache(sprite, l);
		} catch (Exception exception) {
			return null;
		}
		return sprite;
	}

	public static void addTooltipBox(int id, String text) {
		RSInterface rsi = addInterface(id);
		rsi.id = id;
		rsi.parentID = id;
		rsi.type = 9;
		rsi.message = text;
	}

	public static void addTooltip(int id, String text) {
		RSInterface rsi = addInterface(id);
		rsi.id = id;
		rsi.type = 0;
		rsi.isMouseoverTriggered = true;
		rsi.mOverInterToTrigger = -1;
		addTooltipBox(id + 1, text);
		rsi.totalChildren(1);
		rsi.child(0, id + 1, 0, 0);
	}

	public static void addText(int i, String s, int k, boolean l, boolean m,
			int a, TextDrawingArea[] TDA, int j) {
		RSInterface RSInterface = addInterface(i);
		RSInterface.parentID = i;
		RSInterface.id = i;
		RSInterface.type = 4;
		RSInterface.atActionType = 0;
		RSInterface.width = 0;
		RSInterface.height = 0;
		RSInterface.contentType = 0;
		RSInterface.aByte254 = 0;
		RSInterface.mOverInterToTrigger = a;
		RSInterface.centerText = l;
		RSInterface.textShadow = m;
		RSInterface.textDrawingAreas = TDA[j];
		RSInterface.message = s;
		RSInterface.aString228 = "";
		RSInterface.textColor = k;
	}

	public static void addSprite(int i, int j, int k) {
		RSInterface rsinterface = interfaceCache[i] = new RSInterface();
		rsinterface.id = i;
		rsinterface.parentID = i;
		rsinterface.type = 5;
		rsinterface.atActionType = 1;
		rsinterface.contentType = 0;
		rsinterface.width = 20;
		rsinterface.height = 20;
		rsinterface.aByte254 = 0;
		rsinterface.mOverInterToTrigger = 52;
		rsinterface.sprite1 = imageLoader(j, "Equipment/SPRITE");
		rsinterface.sprite2 = imageLoader(k, "Equipment/SPRITE");
	}

	public static void addHover(int i, int aT, int cT, int hoverid, int sId,
			String NAME, int W, int H, String tip) {
		RSInterface rsinterfaceHover = addInterface(i);
		rsinterfaceHover.id = i;
		rsinterfaceHover.parentID = i;
		rsinterfaceHover.type = 5;
		rsinterfaceHover.atActionType = aT;
		rsinterfaceHover.contentType = cT;
		rsinterfaceHover.mOverInterToTrigger = hoverid;
		rsinterfaceHover.sprite1 = imageLoader(sId, NAME);
		rsinterfaceHover.sprite2 = imageLoader(sId, NAME);
		rsinterfaceHover.width = W;
		rsinterfaceHover.height = H;
		rsinterfaceHover.tooltip = tip;
	}

	public static void addHovered(int i, int j, String imageName, int w, int h,
			int IMAGEID) {
		RSInterface rsinterfaceHover = addInterface(i);
		rsinterfaceHover.parentID = i;
		rsinterfaceHover.id = i;
		rsinterfaceHover.type = 0;
		rsinterfaceHover.atActionType = 0;
		rsinterfaceHover.width = w;
		rsinterfaceHover.height = h;
		rsinterfaceHover.isMouseoverTriggered = true;
		rsinterfaceHover.mOverInterToTrigger = -1;
		addSprite(IMAGEID, j, imageName);
		setChildren(1, rsinterfaceHover);
		setBounds(IMAGEID, 0, 0, 0, rsinterfaceHover);
	}

	public static void addText(int id, String text, TextDrawingArea tda[],
			int idx, int color, boolean centered) {
		RSInterface rsi = interfaceCache[id] = new RSInterface();
		if (centered)
			rsi.centerText = true;
		rsi.textShadow = true;
		rsi.textDrawingAreas = tda[idx];
		rsi.message = text;
		rsi.textColor = color;
		rsi.id = id;
		rsi.type = 4;
	}

	public static void addText(int id, String text, TextDrawingArea tda[],
			int idx, int color, boolean center, boolean shadow) {
		RSInterface tab = addTabInterface(id);
		tab.parentID = id;
		tab.id = id;
		tab.type = 4;
		tab.atActionType = 0;
		tab.width = 0;
		tab.height = 11;
		tab.contentType = 0;
		tab.aByte254 = 0;
		tab.mOverInterToTrigger = -1;
		tab.centerText = center;
		tab.textShadow = shadow;
		tab.textDrawingAreas = tda[idx];
		tab.message = text;
		tab.aString228 = "";
		tab.textColor = color;
		tab.anInt219 = 0;
		tab.anInt216 = 0;
		tab.anInt239 = 0;
	}

	public String hoverText;
	private int opacity;
	private int hoverType;
	private boolean inventoryHover;

	public static void addHoverBox(int id, String text) {
		RSInterface rsi = interfaceCache[id];// addTabInterface(id);
		rsi.id = id;
		rsi.parentID = id;
		rsi.isMouseoverTriggered = true;
		rsi.type = 8;
		rsi.hoverText = text;
	}

	public static void addButton(int id, int sid, String spriteName,
			String tooltip) {
		RSInterface tab = interfaceCache[id] = new RSInterface();
		tab.id = id;
		tab.parentID = id;
		tab.type = 5;
		tab.atActionType = 1;
		tab.contentType = 0;
		tab.aByte254 = (byte) 0;
		tab.mOverInterToTrigger = 52;
		tab.sprite1 = imageLoader(sid, spriteName);
		tab.sprite2 = imageLoader(sid, spriteName);
		tab.width = tab.sprite1.myWidth;
		tab.height = tab.sprite2.myHeight;
		tab.tooltip = tooltip;
	}

	public static void addHoverBox(int id, int ParentID, String text,
			String text2, int configId, int configFrame) {
		RSInterface rsi = addTabInterface(id);
		rsi.id = id;
		rsi.parentID = ParentID;
		rsi.type = 8;
		rsi.aString228 = text;
		rsi.message = text2;
		rsi.anIntArray245 = new int[1];
		rsi.anIntArray212 = new int[1];
		rsi.anIntArray245[0] = 1;
		rsi.anIntArray212[0] = configId;
		rsi.valueIndexArray = new int[1][3];
		rsi.valueIndexArray[0][0] = 5;
		rsi.valueIndexArray[0][1] = configFrame;
		rsi.valueIndexArray[0][2] = 0;
	}

	public static void addSprite(int ID, int i, int i2, String name,
			int configId, int configFrame) {
		RSInterface Tab = addTabInterface(ID);
		Tab.id = ID;
		Tab.parentID = ID;
		Tab.type = 5;
		Tab.atActionType = 0;
		Tab.contentType = 0;
		Tab.width = 512;
		Tab.height = 334;
		Tab.aByte254 = (byte) 0;
		Tab.anIntArray245 = new int[1];
		Tab.anIntArray212 = new int[1];
		Tab.anIntArray245[0] = 1;
		Tab.anIntArray212[0] = configId;
		Tab.valueIndexArray = new int[1][3];
		Tab.valueIndexArray[0][0] = 5;
		Tab.valueIndexArray[0][1] = configFrame;
		Tab.valueIndexArray[0][2] = 0;
		Tab.sprite1 = imageLoader(i, name);
		Tab.sprite2 = imageLoader(i2, name);
	}

	public static void addSprite(int id, int spriteId, String spriteName) {
		RSInterface tab = interfaceCache[id] = new RSInterface();
		tab.id = id;
		tab.parentID = id;
		tab.type = 5;
		tab.atActionType = 0;
		tab.contentType = 0;
		tab.aByte254 = (byte) 0;
		tab.mOverInterToTrigger = 52;
		tab.sprite1 = imageLoader(spriteId, spriteName);
		tab.sprite2 = imageLoader(spriteId, spriteName);
		tab.width = 512;
		tab.height = 334;
	}

	public static void addHoverButton(int i, String imageName, int j,
			int width, int height, String text, int contentType, int hoverOver,
			int aT) {// hoverable button
		RSInterface tab = addTabInterface(i);
		tab.id = i;
		tab.parentID = i;
		tab.type = 5;
		tab.atActionType = aT;
		tab.contentType = contentType;
		tab.aByte254 = 0;
		tab.mOverInterToTrigger = hoverOver;
		tab.sprite1 = imageLoader(j, imageName);
		tab.sprite2 = imageLoader(j, imageName);
		tab.width = width;
		tab.height = height;
		tab.tooltip = text;
	}

	public static void addHoveredButton(int i, String imageName, int j, int w,
			int h, int IMAGEID) {// hoverable button
		RSInterface tab = addTabInterface(i);
		tab.parentID = i;
		tab.id = i;
		tab.type = 0;
		tab.atActionType = 0;
		tab.width = w;
		tab.height = h;
		tab.isMouseoverTriggered = true;
		tab.aByte254 = 0;
		tab.mOverInterToTrigger = -1;
		tab.scrollMax = 0;
		addHoverImage(IMAGEID, j, j, imageName);
		tab.totalChildren(1);
		tab.child(0, IMAGEID, 0, 0);
	}

	public static void addHoverImage(int i, int j, int k, String name) {
		RSInterface tab = addTabInterface(i);
		tab.id = i;
		tab.parentID = i;
		tab.type = 5;
		tab.atActionType = 0;
		tab.contentType = 0;
		tab.width = 512;
		tab.height = 334;
		tab.aByte254 = 0;
		tab.mOverInterToTrigger = 52;
		tab.sprite1 = imageLoader(j, name);
		tab.sprite2 = imageLoader(k, name);
	}

	public static void addTransparentSprite(int id, int spriteId,
			String spriteName) {
		RSInterface tab = interfaceCache[id] = new RSInterface();
		tab.id = id;
		tab.parentID = id;
		tab.type = 5;
		tab.atActionType = 0;
		tab.contentType = 0;
		tab.aByte254 = (byte) 0;
		tab.mOverInterToTrigger = 52;
		tab.sprite1 = imageLoader(spriteId, spriteName);
		tab.sprite2 = imageLoader(spriteId, spriteName);
		tab.width = 512;
		tab.height = 334;
		tab.drawsTransparent = true;
	}

	public static RSInterface addScreenInterface(int id) {
		RSInterface tab = interfaceCache[id] = new RSInterface();
		tab.id = id;
		tab.parentID = id;
		tab.type = 0;
		tab.atActionType = 0;
		tab.contentType = 0;
		tab.width = 512;
		tab.height = 334;
		tab.aByte254 = (byte) 0;
		tab.mOverInterToTrigger = 0;
		return tab;
	}

	public static RSInterface addTabInterface(int id) {
		RSInterface tab = interfaceCache[id] = new RSInterface();
		tab.id = id;// 250
		tab.parentID = id;// 236
		tab.type = 0;// 262
		tab.atActionType = 0;// 217
		tab.contentType = 0;
		tab.width = 512;// 220
		tab.height = 700;// 267
		tab.aByte254 = (byte) 0;
		tab.mOverInterToTrigger = -1;// Int 230
		return tab;
	}

	protected static Sprite imageLoader(int i, String s) {
		long l = (TextClass.method585(s) << 8) + i;
		Sprite sprite = (Sprite) aMRUNodes_238.insertFromCache(l);
		if (sprite != null)
			return sprite;
		try {
			sprite = new Sprite(s + " " + i);
			aMRUNodes_238.removeFromCache(sprite, l);
		} catch (Exception exception) {
			return null;
		}
		return sprite;
	}

	public void child(int id, int interID, int x, int y) {
		children[id] = interID;
		childX[id] = x;
		childY[id] = y;
	}

	public void totalChildren(int t) {
		children = new int[t];
		childX = new int[t];
		childY = new int[t];
	}

	private Model method206(int i, int j) {
		Model model = (Model) aMRUNodes_264.insertFromCache((i << 16) + j);
		if (model != null)
			return model;
		if (i == 1)
			model = Model.method462(j);
		if (i == 2)
			model = EntityDef.forID(j).method160();
		if (i == 3)
			model = client.myPlayer.method453();
		if (i == 4)
			model = ItemDef.forID(j).method202(50);
		if (i == 5)
			model = null;
		if (model != null)
			aMRUNodes_264.removeFromCache(model, (i << 16) + j);
		return model;
	}

	private static Sprite method207(int i, StreamLoader streamLoader, String s) {
		long l = (TextClass.method585(s) << 8) + i;
		Sprite sprite = (Sprite) aMRUNodes_238.insertFromCache(l);
		if (sprite != null)
			return sprite;
		try {
			sprite = new Sprite(streamLoader, s, i);
			aMRUNodes_238.removeFromCache(sprite, l);
		} catch (Exception _ex) {
			return null;
		}
		return sprite;
	}

	public static void method208(boolean flag, Model model) {
		int i = 0;// was parameter
		int j = 5;// was parameter
		if (flag)
			return;
		aMRUNodes_264.unlinkAll();
		if (model != null && j != 4)
			aMRUNodes_264.removeFromCache(model, (j << 16) + i);
	}

	public Model method209(int j, int k, boolean flag) {
		Model model;
		if (flag)
			model = method206(anInt255, anInt256);
		else
			model = method206(anInt233, mediaID);
		if (model == null)
			return null;
		if (k == -1 && j == -1 && model.anIntArray1640 == null)
			return model;
		Model model_1 = new Model(true, Class36.method532(k)
				& Class36.method532(j), false, model);
		if (k != -1 || j != -1)
			model_1.method469();
		if (k != -1)
			model_1.method470(k);
		if (j != -1)
			model_1.method470(j);
		model_1.method479(64, 768, -50, -10, -50, true);
		return model_1;
	}

	public static void AddInterfaceButton(int id, int sid, String spriteName,
			String tooltip, int mOver, int atAction, int width, int height) {
		RSInterface tab = interfaceCache[id] = new RSInterface();
		tab.id = id;
		tab.parentID = id;
		tab.type = 5;
		tab.atActionType = atAction;
		tab.contentType = 0;
		tab.opacity = 0;
		tab.hoverType = mOver;
		tab.sprite1 = imageLoader(sid, spriteName);
		tab.sprite2 = imageLoader(sid, spriteName);
		tab.width = width;
		tab.height = height;
		tab.tooltip = tooltip;
		tab.inventoryHover = true;
	}

	public RSInterface() {
	}

	public static StreamLoader aClass44;
	public boolean drawsTransparent;
	public Sprite sprite1;
	public int anInt208;
	public Sprite sprites[];
	public static RSInterface interfaceCache[];
	public int anIntArray212[];
	public int contentType;// anInt214
	public int spritesX[];
	public int anInt216;
	public int atActionType;
	public String spellName;
	public int anInt219;
	public int width;
	public String tooltip;
	public String selectedActionName;
	public boolean centerText;
	public int scrollPosition;
	public String actions[];
	public int valueCompareType[];
	public int requiredValues[];
	public int valueIndexArray[][];
	public boolean aBoolean227;
	public String aString228;
	public int mOverInterToTrigger;
	public int invSpritePadX;
	public int textColor;
	public int anInt233;
	public int mediaID;
	public boolean aBoolean235;
	public int parentID;
	public int spellUsableOn;
	private static MRUNodes aMRUNodes_238;
	public int anInt239;
	public int children[];
	public int childX[];
	public boolean usableItemInterface;
	public TextDrawingArea textDrawingAreas;
	public int invSpritePadY;
	public int anIntArray245[];
	public int anInt246;
	public int spritesY[];
	public String message;
	public boolean isInventoryInterface;
	public int id;
	public int invStackSizes[];
	public int inv[];
	public byte aByte254;
	private int anInt255;
	private int anInt256;
	public int anInt257;
	public int anInt258;
	public boolean aBoolean259;
	public Sprite sprite2;
	public int scrollMax;
	public int type;
	public int anInt263;
	private static final MRUNodes aMRUNodes_264 = new MRUNodes(30);
	public int anInt265;
	public boolean isMouseoverTriggered;
	public int height;
	public boolean textShadow;
	public int modelZoom;
	public int modelRotation1;
	public int modelRotation2;
	public int childY[];
	public boolean inventoryhover;

	public static void AddInterfaceButton(int id, int sid, String spriteName,
			String tooltip) {
		RSInterface tab = interfaceCache[id] = new RSInterface();
		tab.id = id;
		tab.parentID = id;
		tab.type = 5;
		tab.atActionType = 1;
		tab.contentType = 0;
		tab.opacity = (byte) 0;
		tab.hoverType = 52;
		tab.sprite1 = imageLoader(sid, spriteName);
		tab.sprite2 = imageLoader(sid, spriteName);
		tab.width = tab.sprite1.myWidth;
		tab.height = tab.sprite2.myHeight;
		tab.tooltip = tooltip;
	}

	public static void AddInterfaceButton(int i, int j, String name, int W,
			int H, String S, int AT) {
		RSInterface RSInterface = addInterface(i);
		RSInterface.id = i;
		RSInterface.parentID = i;
		RSInterface.type = 5;
		RSInterface.atActionType = AT;
		RSInterface.contentType = 0;
		RSInterface.opacity = 0;
		RSInterface.hoverType = 52;
		RSInterface.sprite1 = imageLoader(j, name);
		RSInterface.sprite2 = imageLoader(j, name);
		RSInterface.width = W;
		RSInterface.height = H;
		RSInterface.tooltip = S;
	}

	public static void AddInterfaceButton(int id, int sid, String spriteName,
			String tooltip, int w, int h) {
		RSInterface tab = interfaceCache[id] = new RSInterface();
		tab.id = id;
		tab.parentID = id;
		tab.type = 5;
		tab.atActionType = 1;
		tab.contentType = 0;
		tab.opacity = (byte) 0;
		tab.hoverType = 52;
		tab.sprite1 = imageLoader(sid, spriteName);
		tab.sprite2 = imageLoader(sid, spriteName);
		tab.width = w;
		tab.height = h;
		tab.tooltip = tooltip;
	}

	public static void AddInterfaceButton(int i, int j, int hoverId,
			String name, int W, int H, String S, int AT) {
		RSInterface RSInterface = addInterface(i);
		RSInterface.id = i;
		RSInterface.parentID = i;
		RSInterface.type = 5;
		RSInterface.atActionType = AT;
		RSInterface.opacity = 0;
		RSInterface.hoverType = hoverId;
		RSInterface.sprite1 = imageLoader(j, name);
		RSInterface.sprite2 = imageLoader(j, name);
		RSInterface.width = W;
		RSInterface.height = H;
		RSInterface.tooltip = S;
	}

	private static void AddInterfaceButton(int ID, int type, int hoverID,
			int dS, int eS, String NAME, int W, int H, String text,
			int configFrame, int configId) {
		RSInterface rsinterface = addInterface(ID);
		rsinterface.id = ID;
		rsinterface.parentID = ID;
		rsinterface.type = 5;
		rsinterface.atActionType = type;
		rsinterface.opacity = 0;
		rsinterface.hoverType = hoverID;
		rsinterface.sprite1 = imageLoader(dS, NAME);
		rsinterface.sprite2 = imageLoader(eS, NAME);
		rsinterface.width = W;
		rsinterface.height = H;
		rsinterface.valueCompareType = new int[1];
		rsinterface.requiredValues = new int[1];
		rsinterface.valueCompareType[0] = 1;
		rsinterface.requiredValues[0] = configId;
		rsinterface.valueIndexArray = new int[1][3];
		rsinterface.valueIndexArray[0][0] = 5;
		rsinterface.valueIndexArray[0][1] = configFrame;
		rsinterface.valueIndexArray[0][2] = 0;
		rsinterface.tooltip = text;
	}

}
