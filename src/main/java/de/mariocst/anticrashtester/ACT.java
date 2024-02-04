package de.mariocst.anticrashtester;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.server.DataPacketReceiveEvent;
import cn.nukkit.network.protocol.CommandRequestPacket;
import cn.nukkit.plugin.PluginBase;
import org.apache.commons.lang3.StringUtils;

@SuppressWarnings("unused")
public class ACT extends PluginBase implements Listener {
    private final String prefix = "§8[§4Anti§6Crash§bTester§8] §f";

    @Override
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(this, this);

        this.log("Enabled AntiCrashTester");
    }

    @Override
    public void onDisable() {
        this.log("Disabled AntiCrashTester");
    }

    public void log(String msg) {
        this.getLogger().info(this.prefix + msg);
    }

    public void warn(String msg) {
        this.getLogger().warning(this.prefix + msg);
    }

    @EventHandler
    public void onPacketReceive(DataPacketReceiveEvent event) {
        Player player = event.getPlayer();

        if (event.getPacket() instanceof CommandRequestPacket packet) {
            String msg = packet.command;

            if (StringUtils.countMatches(msg, "@e") > 3 || StringUtils.countMatches(msg, "@a") > 3 ||
                    StringUtils.countMatches(msg, "@r") > 3) { // Other selectors don't cause much/any harm. Nukkit doesn't handle more than one selector anyway,
                                                                    // but we want to get rid of those shitty kids
                this.warn(player.getName() + " tried to test the selector spammer crasher!");
                player.kick("External player error", false);
                event.setCancelled();
            }
        }
    }
}
