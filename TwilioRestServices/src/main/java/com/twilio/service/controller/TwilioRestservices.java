package com.twilio.service.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class TwilioRestservices {

	private static final Logger logger = LogManager.getLogger(TwilioRestservices.class);

	@Value("${service.url}")
	private String baseUrl;

	// Create a room
	@RequestMapping(value = "/createaroom", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public String CreateRoom(@RequestBody String json, @RequestHeader String Authorization) throws RoomExist {
		// String Url = "https://video.twilio.com/v1/Rooms";
		String Url = baseUrl;
		JSONObject jsonObj = new JSONObject(json);
		logger.info(jsonObj);
		HttpHeaders headers = new HttpHeaders();
		RestTemplate restTemplate = new RestTemplate();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.set("Authorization", Authorization);
		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		map.add("UniqueName", jsonObj.getString("UniqueName"));
		map.add("MaxParticipants", jsonObj.getString("MaxParticipants"));
		map.add("Type", jsonObj.getString("Type"));
		if (jsonObj.has("EnableTurn")) {
			map.add("EnableTurn", jsonObj.getString("EnableTurn"));
		}
		if (jsonObj.has("StatusCallback")) {
			map.add("StatusCallback", jsonObj.getString("StatusCallback"));
		}
		if (jsonObj.has("StatusCallbackMethod")) {
			map.add("StatusCallbackMethod", jsonObj.getString("StatusCallbackMethod"));
		}

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
		try {
			ResponseEntity<String> response = restTemplate.postForEntity(Url, request, String.class);
			logger.info(response);
			return response.getBody();

		} catch (Exception ex) {
			logger.info(ex);
			throw new RoomExist();
		}

	}

	// Complete a Room
	@RequestMapping(value = "/completearoom", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> CompleteRoom(@RequestBody String json, @RequestHeader String Authorization) {
		JSONObject jsonObj = new JSONObject(json);
		String Url = baseUrl + jsonObj.getString("room_nama");
		logger.info(Url);
		HttpHeaders headers = new HttpHeaders();
		RestTemplate restTemplate = new RestTemplate();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.set("Authorization", Authorization);
		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		map.add("Status", "completed");
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
		ResponseEntity<String> response = restTemplate.postForEntity(Url, request, String.class);
		return response;
	}

	// Remove a Participant
	@RequestMapping(value = "/removeaparticipant", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> RemoveParticipant(@RequestBody String json, @RequestHeader String Authorization) {
		JSONObject jsonObj = new JSONObject(json);
		String Url = baseUrl + jsonObj.getString("room_name") + "/Participants/"
				+ jsonObj.getString("participant_name");
		logger.info(Url);
		HttpHeaders headers = new HttpHeaders();
		RestTemplate restTemplate = new RestTemplate();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.set("Authorization", Authorization);
		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		map.add("Status", "disconnected");
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
		ResponseEntity<String> response = restTemplate.postForEntity(Url, request, String.class);
		return response;
	}

	// Create a room
	@RequestMapping(value = "/createagrouproom", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public String createagGrouproom(@RequestBody String json, @RequestHeader String Authorization) throws RoomExist {
		// String Url = "https://video.twilio.com/v1/Rooms";
		String Url = baseUrl;
		JSONObject jsonObj = new JSONObject(json);
		logger.info(jsonObj);
		HttpHeaders headers = new HttpHeaders();
		RestTemplate restTemplate = new RestTemplate();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.set("Authorization", Authorization);
		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		map.add("UniqueName", jsonObj.getString("UniqueName"));
		map.add("MaxParticipants", jsonObj.getString("MaxParticipants"));
		map.add("Type", jsonObj.getString("Type"));
		if (jsonObj.has("EnableTurn")) {
			map.add("EnableTurn", jsonObj.getString("EnableTurn"));
		}
		if (jsonObj.has("StatusCallback")) {
			map.add("StatusCallback", jsonObj.getString("StatusCallback"));
		}
		if (jsonObj.has("StatusCallbackMethod")) {
			map.add("StatusCallbackMethod", jsonObj.getString("StatusCallbackMethod"));
		}
		if (jsonObj.has("RecordParticipantsOnConnect")) {
			map.add("RecordParticipantsOnConnect", jsonObj.getString("RecordParticipantsOnConnect"));
		}
		if (jsonObj.has("MediaRegion")) {
			map.add("MediaRegion", jsonObj.getString("MediaRegion"));
		}
		if (jsonObj.has("VideoCodecs")) {
			map.add("VideoCodecs", jsonObj.getString("VideoCodecs"));
		}

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
		try {
			ResponseEntity<String> response = restTemplate.postForEntity(Url, request, String.class,
					HttpStatus.CREATED);
			logger.info(response);
			return response.getBody();

		} catch (Exception ex) {
			logger.info(ex);
			throw new RoomExist();
		}
	}

	
	@Value("${service.url1}")
	private String baseUrl1;
	// whatsapp send a message
	@RequestMapping(value = "/sendamessage", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public String sendaMessage(@RequestBody String json, @RequestHeader String Authorization) throws RoomExist {
		String Url = baseUrl1;
		JSONObject jsonObj = new JSONObject(json);
		logger.info(jsonObj);
		HttpHeaders headers = new HttpHeaders();
		RestTemplate restTemplate = new RestTemplate();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.set("Authorization", Authorization);
		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		map.add("Body", jsonObj.getString("Body"));
		map.add("From", jsonObj.getString("From"));
		map.add("To", jsonObj.getString("To"));

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
		ResponseEntity<String> response = restTemplate.postForEntity(Url, request, String.class);
		logger.info(response);
		return response.getBody();

	}
}
