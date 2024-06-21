package LukaFarkas.MedOpremaBackend.controller;


import LukaFarkas.MedOpremaBackend.dto.PenalPointDto;
import LukaFarkas.MedOpremaBackend.service.PenalPointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/penal-points")
public class PenalPointController {
    @Autowired
    private PenalPointService penalPointService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PenalPointDto>> getAllPenalPointsByUserId(@PathVariable Long userId) {
        List<PenalPointDto> penalPoints = penalPointService.getAllPenalPointsByUserId(userId);
        return new ResponseEntity<>(penalPoints, HttpStatus.OK);
    }
}
