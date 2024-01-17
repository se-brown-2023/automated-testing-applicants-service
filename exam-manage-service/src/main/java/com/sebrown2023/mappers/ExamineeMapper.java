package com.sebrown2023.mappers;

import com.sebrown2023.model.db.Examinee;
import com.sebrown2023.model.dto.ExamineeComponent;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ExamineeMapper {

    Examinee examineeComponentToExaminee(ExamineeComponent examineeComponent);

    ExamineeComponent examineeToExamineeComponent(Examinee examineeComponent);
}
