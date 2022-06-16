package com.ssomar.score.utils;

import java.util.ArrayList;
import java.util.List;

public enum PlaceholdersCdtType {

	PLAYER_NUMBER, 
	PLAYER_STRING,
	PLAYER_PLAYER,
	TARGET_NUMBER,
	TARGET_STRING,
	TARGET_TARGET,
	PLAYER_TARGET;


	public static List<PlaceholdersCdtType> getpCdtTypeWithNumber() {
		List<PlaceholdersCdtType> result = new ArrayList<>();
		result.add(PlaceholdersCdtType.PLAYER_NUMBER);
		result.add(PlaceholdersCdtType.TARGET_NUMBER);

		return result;
	}
	
	public static List<PlaceholdersCdtType> getpCdtTypeWithPlayer() {
		List<PlaceholdersCdtType> result = new ArrayList<>();
		result.add(PlaceholdersCdtType.PLAYER_NUMBER);
		result.add(PlaceholdersCdtType.PLAYER_STRING);
		result.add(PlaceholdersCdtType.PLAYER_PLAYER);
		result.add(PlaceholdersCdtType.PLAYER_TARGET);

		return result;
	}
	
	public static List<PlaceholdersCdtType> getpCdtTypeWithTarget() {
		List<PlaceholdersCdtType> result = new ArrayList<>();
		result.add(PlaceholdersCdtType.TARGET_NUMBER);
		result.add(PlaceholdersCdtType.TARGET_STRING);
		result.add(PlaceholdersCdtType.TARGET_TARGET);

		return result;
	}
	
	public static List<PlaceholdersCdtType> getpCdtTypeWithString() {
		List<PlaceholdersCdtType> result = new ArrayList<>();
		result.add(PlaceholdersCdtType.PLAYER_STRING);
		result.add(PlaceholdersCdtType.TARGET_STRING);
		
		return result;
	}
	

	public PlaceholdersCdtType getPrev() {
		switch (this) {
		case PLAYER_NUMBER:
			return  PLAYER_TARGET;
		case PLAYER_STRING:
			return PLAYER_NUMBER;
		case PLAYER_PLAYER:
			return PLAYER_STRING;
		case TARGET_NUMBER:
			return PLAYER_PLAYER;
		case TARGET_STRING:
			return TARGET_NUMBER;
		case TARGET_TARGET:
			return TARGET_STRING;
		case PLAYER_TARGET:
			return TARGET_TARGET;
		default:
			return PLAYER_NUMBER;
		}
	}
	
	
	public PlaceholdersCdtType getNext() {
		switch (this) {
		case PLAYER_NUMBER:
			return PLAYER_STRING;
		case PLAYER_STRING:
			return PLAYER_PLAYER;
		case PLAYER_PLAYER:
			return TARGET_NUMBER;
		case TARGET_NUMBER:
			return TARGET_STRING;
		case TARGET_STRING:
			return TARGET_TARGET;
		case TARGET_TARGET:
			return PLAYER_TARGET;
		case PLAYER_TARGET:
			return PLAYER_NUMBER;
		default:
			return PLAYER_NUMBER;
		}
	}
}
