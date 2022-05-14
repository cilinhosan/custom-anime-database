package com.example.customanimedatabase.controller;

import com.example.customanimedatabase.model.Anime;
import com.example.customanimedatabase.model.AnimeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
public class AnimeController {
    private final AnimeRepository repository;

    AnimeController(AnimeRepository repository){
        this.repository = repository;
    }

    @GetMapping("/anime")
     List<Anime> listAllAnime() {
        return repository.findAll();
    }

    @PostMapping("/anime")
    Anime registerAnime(@RequestBody Anime newAnime) {
        //do the required checks
        //check if name is not null
        if(newAnime.getName() == null || newAnime.getName().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing anime name.");
        }

        //check if date is not null
        if(newAnime.getStartDate() == null || newAnime.getEndDate() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Start date and end date must not be null.");
        }

        //check if endDate is after startDate
        if(newAnime.getStartDate().isAfter(newAnime.getEndDate()) || newAnime.getStartDate().isEqual(newAnime.getEndDate())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "End date must be after start date.");
        }

        return repository.save(newAnime);

    }

    @GetMapping("/anime/{id}")
    Optional<Anime> listSingleAnime(@PathVariable Long id) {
        //if not found send error

        LocalDate currentLocalDate = LocalDate.now();

        Optional<Anime> optionalAnime = repository.findById(id);
        if(optionalAnime.isPresent()){
            return optionalAnime;
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Anime id not found.");
        }
    }

    @PutMapping("/anime/{id}")
    Anime replaceAnime(@RequestBody Anime newAnime, @PathVariable Long id) {

        //do the required checks
        //check if name is not null
        if(newAnime.getName() == null || newAnime.getName().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing anime name.");
        }

        //check if date is not null
        if(newAnime.getStartDate() == null || newAnime.getEndDate() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Start date and end date must be not null.");
        }

        //check if endDate is after startDate
        if(newAnime.getStartDate().isAfter(newAnime.getEndDate()) || newAnime.getStartDate().isEqual(newAnime.getEndDate())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "End date must be after start date.");
        }

        return repository.findById(id)
                .map(anime -> {
                    anime.setName(newAnime.getName());
                    anime.setStartDate(newAnime.getStartDate());
                    anime.setEndDate(newAnime.getEndDate());
                    return repository.save(anime);
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Anime not found.")
                );
    }

    @DeleteMapping("/anime/{id}")
    Optional<Anime> deleteAnime(@PathVariable Long id) {
        Optional<Anime> animeToBeDeleted = repository.findById(id);
        if(animeToBeDeleted.isPresent()){
            repository.deleteById(id);
            return animeToBeDeleted;
        }
        else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Anime not found.");
        }
    }
}
