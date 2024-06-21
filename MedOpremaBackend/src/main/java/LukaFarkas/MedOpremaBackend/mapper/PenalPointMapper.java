package LukaFarkas.MedOpremaBackend.mapper;

import LukaFarkas.MedOpremaBackend.dto.PenalPointDto;
import LukaFarkas.MedOpremaBackend.entity.PenalPoint;
import LukaFarkas.MedOpremaBackend.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PenalPointMapper {

    public static PenalPointDto toDto(PenalPoint penalPoint) {
        return new PenalPointDto(
                penalPoint.getId(),
                penalPoint.getUser().getId(),
                penalPoint.getPoints(),
                penalPoint.getTimestamp()
        );
    }

    public static PenalPoint toEntity(PenalPointDto penalPointDto, User user) {
        return new PenalPoint(
                user,
                penalPointDto.getPoints(),
                penalPointDto.getTimestamp()
        );
    }

    public static List<PenalPointDto> toDtoList(List<PenalPoint> penalPoints) {
        return penalPoints.stream()
                .map(PenalPointMapper::toDto)
                .collect(Collectors.toList());
    }

    public static List<PenalPoint> toEntityList(List<PenalPointDto> penalPointDtos, User user) {
        if (penalPointDtos == null) {
            return new ArrayList<>();
        }
        return penalPointDtos.stream()
                .map(penalPointDto -> toEntity(penalPointDto, user))
                .collect(Collectors.toList());
    }
}
