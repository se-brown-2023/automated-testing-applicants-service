package com.sebrown2023.mappers;

import com.sebrown2023.model.db.Test;
import com.sebrown2023.model.dto.TestComponent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TestMapper {
    @Mapping(target = "expectedOutputData", source = "outputData")
    @Mapping(target = "task", ignore = true)
    @Mapping(target = "id", defaultValue = "0L")
    Test testComponentToTest(TestComponent testComponent);

    @Mapping(target = "taskId", expression = "java(test.getTask() != null ? test.getTask().getId() : null)")
    @Mapping(target = "outputData", source = "expectedOutputData")
    TestComponent testToTestComponent(Test test);

}
