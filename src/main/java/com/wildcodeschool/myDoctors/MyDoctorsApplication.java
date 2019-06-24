package com.wildcodeschool.myDoctors;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

@Controller
@SpringBootApplication
public class MyDoctorsApplication {

	public static String[][] doctors = {
		{"Christopher Eccleston", "9", "13", "41"},
		{"David Tennant", "10", "47", "34"},
		{"Matt Smith", "11", "44", "27"},
		{"Peter Capaldi", "12", "40", "55"},
		{"Jodie Whittaker", "13", "11", "35"}
	};

	class Doctor {
		private String number;
		private String name;

		public Doctor(String name, String number) {
			this.number = number;
			this.name = name;
		}

		public String getNumber() {
			return number;
		}

		public void setNumber(String number) {
			this.number = number;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}

	class ExtendedDoctor extends Doctor {
		private String numberOfEpisodes;
		private String ageAtStart;

		public ExtendedDoctor(String name, String number, String numberOfEpisodes, String ageAtStart){
			super(name, number);
			this.numberOfEpisodes = numberOfEpisodes;
			this.ageAtStart = ageAtStart;
		}

		public String getNumberOfEpisodes() {
			return numberOfEpisodes;
		}

		public void setNumberOfEpisodes(String numberOfEpisodes) {
			this.numberOfEpisodes = numberOfEpisodes;
		}

		public String getAgeAtStart() {
			return ageAtStart;
		}

		public void setAgeAtStart(String ageAtStart) {
			this.ageAtStart = ageAtStart;
		}
	}

	public static void main(String[] args) {
		SpringApplication.run(MyDoctorsApplication.class, args);
	}

	@RequestMapping("/doctor/{number}")
	@ResponseBody
	Doctor myDoctor(@PathVariable int number, @RequestParam(value = "details", required=false) boolean details) {
		if (number >= 9 && number <= 13) {
			if (details) {
				return new ExtendedDoctor(doctors[number-9][0], doctors[number-9][1], doctors[number-9][2], doctors[number-9][3]);
			}
			return new Doctor(doctors[number-9][0], doctors[number-9][1]);
		}
		else if (number >= 1 && number < 9) {
			throw new ResponseStatusException(HttpStatus.SEE_OTHER);
		}
		else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Impossible de récupérer l'incarnation " + number);
		}
	}
}