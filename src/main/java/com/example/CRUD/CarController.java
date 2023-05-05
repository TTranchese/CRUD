package com.example.CRUD;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cars")
public class CarController {
	@Autowired
	CarRepository carRepository;
	
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
		} else return new ResponseEntity<>(new CarEntity(), HttpStatus.NOT_FOUND);
	}
	
	@PutMapping("/update-type")
	public ResponseEntity<CarEntity> updateCarType(@RequestParam int id, @RequestParam String newType){
		CarEntity carEntity = new CarEntity();
		Optional<CarEntity> oldCarEntity = carRepository.findById(id);
		if (oldCarEntity.isPresent()) {
			carEntity=oldCarEntity.get();
			carEntity.setType(newType);
			carRepository.save(carEntity);
			return new ResponseEntity<>(carEntity, HttpStatus.OK);
		} else return new ResponseEntity<>(carEntity, HttpStatus.NOT_FOUND);
	}
	@DeleteMapping("/delete")
	public ResponseEntity<CarEntity> deleteCar(@RequestParam int id){
		CarEntity carEntity = carRepository.findById(id).get();
		if (carRepository.findById(id).isPresent()){
			carRepository.deleteById(id);
			return new ResponseEntity<>(carEntity, HttpStatus.OK);
		} else return new ResponseEntity<>(HttpStatus.CONFLICT);
	}
	@DeleteMapping("/delete-all")
	public ResponseEntity<String> deleteAllCars(){
		carRepository.deleteAll();
		return new ResponseEntity<>("Deleted all the cars", HttpStatus.OK);
	}
}
