package at.htlkaindorf.backend_redogo.mapper;

import at.htlkaindorf.backend_redogo.beans.Location;
import at.htlkaindorf.backend_redogo.dto.LocationDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface LocationMapper {
    Location toEntity(LocationDto locationDto);
    LocationDto toDto(Location location);
    List<Location> toEntityList(List<LocationDto> dto);
    List<LocationDto> toDtoList(List<Location> location);
}