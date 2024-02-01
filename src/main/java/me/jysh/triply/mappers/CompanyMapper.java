package me.jysh.triply.mappers;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import me.jysh.triply.dtos.CompanyEntry;
import me.jysh.triply.entity.CompanyEntity;

@NoArgsConstructor(access = AccessLevel.NONE)
public final class CompanyMapper {

  public static CompanyEntry toEntry(final CompanyEntity entity) {
    final CompanyEntry companyEntry = new CompanyEntry();
    companyEntry.setId(entity.getId());
    companyEntry.setName(entity.getName());
    return companyEntry;
  }

  public static CompanyEntity toEntity(final CompanyEntry entry) {
    final CompanyEntity companyEntity = new CompanyEntity();
    companyEntity.setId(entry.getId());
    companyEntity.setName(entry.getName());
    return companyEntity;
  }
}
