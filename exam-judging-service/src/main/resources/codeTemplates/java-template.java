import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sebrown2023.util.Solution;

public class Main_CLASS_RANDOM_NUMBER {
    public static void main(String[] args) throws JsonProcessingException {
        var objectMapper = new ObjectMapper();
        var inputArg = objectMapper.readValue(args[0], new TypeReference<REPLACE_WITH_RETURN_TYPE>() {
        });
        var result = new Solution().run(inputArg);
        System.out.println("USER_SOLUTION_RESULT: " + objectMapper.writeValueAsString(result));
    }
}
