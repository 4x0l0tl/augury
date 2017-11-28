package com.augury.model;


import org.joda.time.DateTime;
import org.neo4j.ogm.typeconversion.AttributeConverter;

public class JodaTimeConverter implements AttributeConverter<DateTime, Long> {
	@Override
	public Long toGraphProperty(DateTime value) {
		return value.toDateTimeISO().getMillis();
	}

	@Override
	public DateTime toEntityAttribute(Long value) {
		return new DateTime(value);
	}
}
