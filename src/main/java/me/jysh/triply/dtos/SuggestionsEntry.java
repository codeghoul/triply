package me.jysh.triply.dtos;

import java.util.List;

public record SuggestionsEntry(VehicleModelMileageSummaryEntry current,
                               List<VehicleModelMileageSummaryEntry> suggested) {

}
