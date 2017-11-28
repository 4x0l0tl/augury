package com.augury.plugin;

import org.springframework.plugin.core.Plugin;

public interface AuguryPlugin extends Plugin<String> {
	void initialise();
}
