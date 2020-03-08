package com.khjxiaogu.EventDebuger;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;

public class EventDebuger extends JavaPlugin {
	static JavaPlugin plugin;

	@Override
	public void onDisable() {
		super.onDisable();
	}

	@Override
	public void onLoad() {
		saveDefaultConfig();
	}

	@Override
	public void onEnable() {
		EventDebuger.plugin = this;
		// Bukkit.getPluginManager().registerEvent(event,
		// listener,EventPriority.MONITOR,new Logger(), plugin);
		Set<Class<? extends Event>> allevents=new HashSet<>();
		for (String pack : getConfig().getStringList("packages")) {
			Reflections reflections = new Reflections(pack);
			allevents.addAll(reflections.getSubTypesOf(Event.class));
			
		}
		for (Class<? extends Event> ev : allevents) {
			Logger log = new Logger();
			try {
				Bukkit.getPluginManager().registerEvent(ev, new Listener() {
				}, EventPriority.MONITOR, log, EventDebuger.plugin);
			} catch (Throwable t) {
				getLogger().warning("Event " + ev.getName() + "register failed!");
			}

		}
		super.onEnable();
	}

}
