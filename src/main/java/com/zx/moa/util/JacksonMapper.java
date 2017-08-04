package com.zx.moa.util;

import org.codehaus.jackson.map.DeserializationConfig.Feature;
import org.codehaus.jackson.map.ObjectMapper;

public class JacksonMapper {

	private static final ObjectMapper mapper = new ObjectMapper().configure(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);

	private JacksonMapper() {
	}

	public static ObjectMapper getInstance() {

		return mapper;
	}

}
