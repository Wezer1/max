package net.proselyte.trpomax.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.proselyte.trpomax.dto.ToysDTO;
import net.proselyte.trpomax.service.ToysService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/toys")
@RequiredArgsConstructor
public class ToysController {

    private final ToysService toysService;

    @GetMapping("/")
    public ResponseEntity<List<ToysDTO>> getAllToys(){
        return ResponseEntity.ok(toysService.getAllToys());
    }

    @GetMapping("/{toyId}")
    public ResponseEntity<ToysDTO> getToyById(@PathVariable Integer toyId){
        return ResponseEntity.ok(toysService.getToyById(toyId));
    }

    @DeleteMapping("/{toyId}")
    public ResponseEntity<ToysDTO> deleteToy(@PathVariable Integer toyId){
        toysService.deleteToy(toyId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/")
    public ResponseEntity<ToysDTO> saveToy(@Valid @RequestBody ToysDTO toysDTO){
        return ResponseEntity.ok(toysService.saveToy(toysDTO));
    }

    @PostMapping("/{toyId}")
    public ResponseEntity<ToysDTO> changeToy(@PathVariable Integer toyId,
                                            @Valid @RequestBody ToysDTO toysDTO){
        return ResponseEntity.ok(toysService.changeToy(toyId, toysDTO));
    }
    @PostMapping("/{id}/price")
    //пример запроса к данным
    //http://localhost:8080/api/toys/sorted?column=price&ascending=false
    public ResponseEntity<ToysDTO> changeToyPrice(
            @PathVariable("id") Integer toyId,
            @RequestParam("percent") BigDecimal percent) {

        return ResponseEntity.ok(toysService.changePrice(toyId, percent));
    }

    @GetMapping("/maxPrice")
    public ResponseEntity<ToysDTO> maxPrice(){
        return ResponseEntity.ok(toysService.maxPrice());
    }

    @GetMapping("/search")
    //пример запроса к данным
    //http://localhost:8080/api/toys/sorted?column=price&ascending=false
    public ResponseEntity<List<ToysDTO>> searchToys(
            @RequestParam double price,
            @RequestParam int ageFrom,
            @RequestParam int ageTo) {

        return ResponseEntity.ok(toysService.findToys(price, ageFrom, ageTo));
    }

    @GetMapping("/toysByAge")
    //пример запроса к данным
    //http://localhost:8080/api/toys/sorted?column=price&ascending=false
    public ResponseEntity<List<ToysDTO>> searchToys(
            @RequestParam int ageFrom,
            @RequestParam int ageTo) {

        return ResponseEntity.ok(toysService.findToysByAgeRange(ageFrom, ageTo));
    }

    @GetMapping("/sorted")
    //пример запроса к данным
    //http://localhost:8080/api/toys/sorted?column=price&ascending=false
    public List<ToysDTO> getSortedToys(
            @RequestParam String column,
            @RequestParam(defaultValue = "true") boolean ascending) {
        return toysService.getSortedToys(column, ascending);
    }
}
