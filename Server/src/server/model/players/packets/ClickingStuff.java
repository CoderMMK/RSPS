package server.model.players.packets;

import server.model.players.Client;
import server.model.players.PacketType;
import server.model.players.PlayerHandler;

/**
 * Clicking stuff (interfaces)
 **/
public class ClickingStuff implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		if (c.inTrade) {
			if (!c.tradeConfirmed2) {
				Client o = (Client) PlayerHandler.players[c.tradeWith];
				c.getTradeAndDuel().declineTrade();
				o.getTradeAndDuel().declineTrade();
			}	
		}
		// if (c.isBanking)
		// c.isBanking = false;

		Client o = (Client) PlayerHandler.players[c.duelingWith];
		if (c.duelStatus == 5) {
			// c.sendMessage("You're funny sir.");
			return;
		}
		if (o != null) {
			if (c.duelStatus >= 1 && c.duelStatus <= 4) {
				c.getTradeAndDuel().declineDuel();
				o.getTradeAndDuel().declineDuel();
			}
		}

		if (c.duelStatus == 6) {
			c.getTradeAndDuel().claimStakedItems();
		}

	}

}
