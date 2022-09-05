package com.example.application;

import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.util.Base64;
import java.util.UUID;

public class UUIDFactory {
	/**
	 * UUID as urlencoded String.
	 * 
	 * @return String Urlencoded UUID (22 characters)
	 */
	public static String createUUID() {
		final UUID uuid = UUID.randomUUID();
		final ByteBuffer byteBuffer = MappedByteBuffer.allocate(16);
		byteBuffer.putLong(uuid.getMostSignificantBits());
		byteBuffer.putLong(uuid.getLeastSignificantBits());
		return Base64.getUrlEncoder().encodeToString(byteBuffer.array()).substring(0,22);
	}
}
