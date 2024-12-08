package net.proselyte.trpomax.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.proselyte.trpomax.dto.ToysDTO;
import net.proselyte.trpomax.entity.Toys;
import net.proselyte.trpomax.exceptions.NoSuchException;
import net.proselyte.trpomax.mapper.ToysMapper;
import net.proselyte.trpomax.repository.ToysRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ToysService {

    private final ToysRepository toysRepository;
    private final ToysMapper toysMapper;

    @Transactional
    public List<ToysDTO> getAllToys() {
        log.info("Get all toys");
        if (toysRepository.findAll().isEmpty()) {
            throw new NoSuchException("No toys");
        }
        return toysRepository.findAll().stream().map(toysMapper::toDto).collect(Collectors.toList());
    }

    @Transactional
    public ToysDTO getToyById(Integer toyId) {
        log.info("Get toy by id: {} ", toyId);
        Optional<Toys> toyOptional = Optional.ofNullable(toysRepository.findById(toyId)
                .orElseThrow(() -> new NoSuchException("There is no toy with ID = " + toyId + " in DB")));
        return toysMapper.toDto(toyOptional.get());
    }

    @Transactional
    public void deleteToy(Integer toyId) {
        log.info("Delete toy");
        if (toysRepository.findById(toyId).isEmpty()) {
            throw new NoSuchException("There is no toy with ID = " + toyId + " in Database");
        } else
            toysRepository.deleteById(toyId);
    }

    @Transactional
    public ToysDTO changeToy(Integer toyId, ToysDTO toysDTO) {
        Optional<Toys> optionalToys = toysRepository.findById(toyId);
        if (optionalToys.isEmpty()) {
            throw new NoSuchException("There is no toy with ID = " + toyId + " in Database");
        } else {
            Toys existingToy = optionalToys.get();
            Toys updatedToy = toysMapper.toEntity(toysDTO);
            existingToy.setName(updatedToy.getName());
            existingToy.setPrice(updatedToy.getPrice());
            existingToy.setQuantity(updatedToy.getQuantity());
            existingToy.setMin(updatedToy.getMin());
            existingToy.setMax(updatedToy.getMax());

            return toysMapper.toDto(toysRepository.save(existingToy));
        }
    }

    @Transactional
    public ToysDTO changePrice(Integer toyId, BigDecimal percent) {
        Optional<Toys> optionalToys = toysRepository.findById(toyId);
        BigDecimal d = new BigDecimal("100.00");
        BigDecimal a = new BigDecimal("1.00");
        BigDecimal p = percent.divide(d).setScale(2, RoundingMode.HALF_UP);
        BigDecimal r = a.add(p);
        if (optionalToys.isEmpty()) {
            throw new NoSuchException("There is no toy with ID = " + toyId + " in Database");
        } else {
            Toys existingToy = optionalToys.get();
            existingToy.setPrice(existingToy.getPrice().multiply(r));

            return toysMapper.toDto(toysRepository.save(existingToy));
        }
    }

    @Transactional
    public ToysDTO saveToy(ToysDTO toysDTO) {
        log.info("Saving toy: {}", toysDTO);
        Toys savedToy = toysRepository.save(toysMapper.toEntity(toysDTO));
        return toysMapper.toDto(savedToy);
    }

    @Transactional
    public ToysDTO maxPrice() {
        return toysMapper.toDto(toysRepository.findMostExpensiveToy());
    }

    @Transactional
    public List<ToysDTO> findToys(double maxPrice, int min, int max) {
        return toysRepository.findToysByPriceAndAgeRange(maxPrice, min, max)
                .stream().map(toysMapper::toDto).collect(Collectors.toList());
    }
    @Transactional
    public List<ToysDTO> findToysByAgeRange(int min, int max) {
        return toysRepository.findToysByAgeRange(min, max)
                .stream().map(toysMapper::toDto).collect(Collectors.toList());
    }

    @Transactional
    public List<ToysDTO> getSortedToys(String column, boolean ascending) {
        Sort sort = ascending ? Sort.by(column).ascending() : Sort.by(column).descending();
        List<Toys> toys = toysRepository.findAll(sort);
        return toys.stream()
                .map(toysMapper::toDto)
                .toList();
    }
}



