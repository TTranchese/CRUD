package com.example.CRUD;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cars")
public class CarController {
	@Autowired
	private CarRepository carRepository;
	
	@PostMapping
	public ResponseEntity<String> insertCar(@RequestBody CarEntity car) {
		carRepository.save(car);
		return new ResponseEntity<>("Created!", HttpStatus.CREATED);
		
	}
	
	@GetMapping
	public ResponseEntity<List<CarEntity>> returnAllCars() {
		return new ResponseEntity<>(carRepository.findAll(), HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<CarEntity> returnCarById(@PathVariable int id) {
		Optional<CarEntity> carEntity = carRepository.findById(id);
		if (carEntity.isPresent()) {
			return new ResponseEntity<>(carEntity.get(), HttpStatus.FOUND);
		} else {
			return new ResponseEntity<>(new CarEntity(), HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<CarEntity> updateCarType(@PathVariable int id, @RequestBody CarEntity newCar) {
		if (carRepository.existsById(id)) {
			newCar.setId(id);
			carRepository.save(newCar);
			return new ResponseEntity<>(newCar, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<CarEntity> deleteCar(@PathVariable int id) {
		Optional<CarEntity> optionalCarEntity = carRepository.findById(id);
		if (optionalCarEntity.isPresent()) {
			CarEntity carEntity = optionalCarEntity.get();
			carRepository.deleteById(id);
			return new ResponseEntity<>(carEntity, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/delete-all")
	public ResponseEntity<String> deleteAllCars() {
		carRepository.deleteAll();
		return new ResponseEntity<>("Deleted all the cars", HttpStatus.OK);
	}
}
