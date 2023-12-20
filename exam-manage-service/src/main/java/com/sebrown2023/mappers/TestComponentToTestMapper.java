package com.sebrown2023.mappers;

import com.sebrown2023.model.db.Test;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Deprecated
@Mapper(componentModel = "spring")
public interface TestComponentToTestMapper {

//    @Mapping(target = "expectedOutputData", source = "outputData")
//    Test convert(TestComponent testComponent);
//
//    @Mapping(target = "outputData", source = "expectedOutputData")
//    @Mapping(target = "taskId", source = "task.id")
//    TestComponent convertBack(Test test);
}
