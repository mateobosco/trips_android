package trips.tdp.fi.uba.ar.tripsandroid;

import trips.tdp.fi.uba.ar.tripsandroid.model.Attraction;

/**
 * Created by joako on 19/3/17.
 */

public class BackEndClient {

    public String[] getCitiesNames(){
        return new String[] {
                "Paris", "Roma", "Venecia", "Krakovia", "Mardel","Buenos Aires",
                "New York", "Londres", "Sao paulo", "Valencia","Madrid", "Barcelona"
        };
    }

    public Attraction getAttraction(){
        Attraction a = new Attraction("Torre Eiffel", "La Torre Eiffel es el símbolo de París, fue construida para la Exposición Universal de París de 1889 y actualmente es el monumento más visitado del mundo.");
        a.setCost(10);
        a.setLatitude(0.2f);
        a.setLongitude(2.3f);
        a.setAverageTime(120);
        a.setScheduleTime("Lu-Do 9:00 - 18:00");

        return a;
    }
}
