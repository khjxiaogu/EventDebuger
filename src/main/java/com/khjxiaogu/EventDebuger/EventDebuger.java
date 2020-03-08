package com.khjxiaogu.EventDebuger;

import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;


public class EventDebuger extends JavaPlugin{
	static JavaPlugin plugin;

	@Override
	public void onDisable() {
		super.onDisable();
	}
	@Override
	public void onLoad() {
		this.saveDefaultConfig();
	}
	@Override
	public void onEnable() {
		plugin=this;
		//Bukkit.getPluginManager().registerEvent(event, listener,EventPriority.MONITOR,new Logger(), plugin);
		for(String pack:getConfig().getStringList("packages")) {
		Reflections reflections = new Reflections(pack);
			Set<Class<? extends Event>> events = 
				    reflections.getSubTypesOf(Event.class);
			for(Class<? extends Event> ev:events) {
				Logger log=new Logger();
				try {
					Bukkit.getPluginManager().registerEvent(ev,new Listener() {},EventPriority.MONITOR,log, plugin);
				}catch(Throwable t) {
					getLogger().warning("Event "+ev.getName()+"register failed!");
				}
				
			}
		}
		super.onEnable();
	}


}
