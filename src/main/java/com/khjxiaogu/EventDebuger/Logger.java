package com.khjxiaogu.EventDebuger;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.plugin.EventExecutor;
import org.w3c.dom.events.EventException;

public class Logger implements EventExecutor {

	public Logger() {
	}

	@Override
	public void execute(Listener listener, Event event) throws EventException {
		if (event != null) {
			StringBuilder sb = new StringBuilder(event.getEventName());
			sb.append("[");
			boolean nfirst = false;
			Class<?> clazz = event.getClass();
			while (clazz != Object.class) {
				for (Field f : clazz.getDeclaredFields()) {
					f.setAccessible(true);
					if (!Modifier.isStatic(f.getModifiers())) {
						try {
							if (nfirst) {
								sb.append(",");
							} else {
								nfirst = true;
							}
							sb.append(String.format("%s=%s", f.getName(), String.format("%s",f.get(event))));
						} catch (IllegalArgumentException e) {
						} catch (IllegalAccessException e) {
						}
					}
				}
				clazz = clazz.getSuperclass();
			}
			sb.append("]");
			EventDebuger.plugin.getLogger().info(sb.toString());
		}
	}
}
