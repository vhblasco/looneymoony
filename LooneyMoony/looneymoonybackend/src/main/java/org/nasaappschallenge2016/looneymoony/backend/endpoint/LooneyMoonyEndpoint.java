package org.nasaappschallenge2016.looneymoony.backend.endpoint;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

import org.nasaappschallenge2016.looneymoony.backend.bean.FunFact;
import org.nasaappschallenge2016.looneymoony.backend.bean.MoonStory;
import org.nasaappschallenge2016.looneymoony.backend.json.LMJSONParser;

import java.util.List;
import java.util.Random;


/**
 * Created by vhblasco on 23/04/16.
 */
/** An endpoint class we are exposing */
@Api(
        name = "lmApi",
        version = "v1",
        namespace = @ApiNamespace(
                ownerDomain = "backend.looneymoony.nasaappschallenge2016.org",
                ownerName = "backend.looneymoony.nasaappschallenge2016.org",
                packagePath=""
        )
)
public class LooneyMoonyEndpoint {

    private LMJSONParser parser = new LMJSONParser();

    private List<FunFact> funfactList = null;
    private List<MoonStory> moonStoriesList = null;

    private Random rndFF = new Random(System.nanoTime());
    private Random rndMS = new Random(System.nanoTime());

    /** A simple endpoint method that takes a name and says Hi back */
    @ApiMethod(name = "getFanFact")
    public FunFact getFanFact() {
        FunFact response = null;

        try {
            if (null == funfactList) {
                initFunFactList();
            }


            int max = funfactList.size();
            int index = rndFF.nextInt((max - 0) + 1);

            response = funfactList.get(index);
        }catch (Exception e){

        }

        return response;
    }

    /** A simple endpoint method that takes a name and says Hi back */
    @ApiMethod(name = "getMoonStory")
    public MoonStory getMoonStory() {
        MoonStory response = null;

        try {
            if (null == moonStoriesList) {
                initMoonStoriesList();
            }


            int max = moonStoriesList.size();
            int index = rndMS.nextInt((max - 0) + 1);

            response = moonStoriesList.get(index);
        }catch (Exception e){

        }

        return response;
    }

    private void initMoonStoriesList()  throws Exception{

        moonStoriesList = parser.getMoonStory("moon_stories.json");

    }
    private void initFunFactList()  throws Exception{

        funfactList = parser.getFunFacts("fun_facts.json");

    }

}
