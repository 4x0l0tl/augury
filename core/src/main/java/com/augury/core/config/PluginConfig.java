package com.augury.core.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.plugin.core.OrderAwarePluginRegistry;
import org.springframework.plugin.core.PluginRegistry;
import org.springframework.plugin.core.config.EnablePluginRegistries;

import com.augury.plugin.it.IssueTrackerPlugin;
import com.augury.plugin.scm.SourceControlPlugin;

@Configuration
@PropertySource(value = { "/application.properties" })
@EnablePluginRegistries({ IssueTrackerPlugin.class, SourceControlPlugin.class })
@ComponentScan("com.augury.plugin")
public class PluginConfig {

	@Bean
	public PluginRegistry<IssueTrackerPlugin, String> itPlugins(List<IssueTrackerPlugin> plugins) {
		return OrderAwarePluginRegistry.create(plugins);
	}

	@Bean
	public PluginRegistry<SourceControlPlugin, String> scmPlugins(List<? extends SourceControlPlugin> plugins) {
		return OrderAwarePluginRegistry.create(plugins);
	}
}
