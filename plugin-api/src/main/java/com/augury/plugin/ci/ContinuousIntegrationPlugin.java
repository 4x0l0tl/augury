package com.augury.plugin.ci;

import java.util.List;

import com.augury.plugin.AuguryPlugin;

public interface ContinuousIntegrationPlugin extends AuguryPlugin{
	public List<String> getJobs();
}
