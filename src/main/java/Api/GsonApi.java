package Api;

import Dto.Root;
import com.google.gson.Gson;

public class GsonApi {

    public Root gsonData (int start, int end) {
        String jsonData = GetApiData.run(start, end);

        Gson gson = new Gson();
        Root root = gson.fromJson(jsonData, Root.class);

        return root;
    }

}
