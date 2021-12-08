package com.andromeda.foodcare.service;

import com.andromeda.dto.NearestBusinessResponse;
import com.andromeda.foodcare.mapper.BusinessMapper;
import com.andromeda.foodcare.model.Address;
import com.andromeda.foodcare.model.Business;
import com.andromeda.foodcare.model.BusinessComparator;
import com.andromeda.foodcare.repository.BusinessRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.cloudinary.json.JSONArray;
import org.cloudinary.json.JSONObject;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@Service
public class DistanceService {

    private final BusinessRepository businessRepository;
    private final String GOOGLE_API_KEY = "AIzaSyBsNKAV-APTasBbztvSp-wXnZSxHSwguZI";
    private final BusinessMapper businessMapper;

    public List<NearestBusinessResponse> getNearestBusinesses(String city, String street, String streetNumber,
                                                              double longitude, double latitude) {
        log.info("Calculating distances of businesses from user.");

        JSONObject userCoordinates;

        if (!city.isEmpty()) {
            String userAddress = streetNumber + " " + street + " " + city;
            userCoordinates = getCoordinates(getLocationInfo(userAddress));
        } else {
            userCoordinates = new JSONObject();
            userCoordinates.put("lng", longitude);
            userCoordinates.put("lat", latitude);
            city = getCityFromCoordinates(longitude, latitude);
        }

        return getNearestBusinessResponses(city, userCoordinates);
    }

    @NotNull
    private List<NearestBusinessResponse> getNearestBusinessResponses(String city, JSONObject userCoordinates) {
        List<Long> businessesIds = businessRepository.getAllByAddress_City(city).stream()
                .map(Business::getId)
                .collect(Collectors.toList());

        List<NearestBusinessResponse> nearestBusinessResponseList = new ArrayList<>();
        businessesIds.forEach(id -> {
            String businessAddress = generateValidAddress(id);
            JSONObject businessCoordinates = getCoordinates(getLocationInfo(businessAddress));

            int distance = findDistanceBetweenLocations(userCoordinates, businessCoordinates);

            NearestBusinessResponse nearestBusinessResponseTemp =
                    businessMapper.toNearestBusinessResponse(businessRepository.getById(id));

            nearestBusinessResponseTemp.setDistance(distance);
            nearestBusinessResponseList.add(nearestBusinessResponseTemp);
        });

        BusinessComparator businessComparator = new BusinessComparator();
        nearestBusinessResponseList.sort(businessComparator);

        return nearestBusinessResponseList;
    }

    public List<NearestBusinessResponse> getNearestBusinessesFromCoordinates(double lat, double lon) {
        log.info("Calculating distances of businesses from user.");

        JSONObject userCoordinates;
        String city = getCityFromCoordinates(lon, lat);
        userCoordinates = new JSONObject();
        userCoordinates.put("lng", lon);
        userCoordinates.put("lat", lat);


        return getNearestBusinessResponses(city, userCoordinates);
    }


    public String getCityFromCoordinates(double lng, double lat) {

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();

        Request request = new Request.Builder()
                .url("https://maps.googleapis.com/maps/api/geocode/json?latlng=" + lat + ","
                        + lng + "&key=" + GOOGLE_API_KEY)
                .method("GET", null)
                .build();

        Response response;
        String city = "";

        try {
            response = client.newCall(request).execute();
            if (response.body() != null) {
                String responseBodyString = Objects.requireNonNull(response.body()).string();
                JSONObject responseJSON = new JSONObject(responseBodyString);
                city = responseJSON.getJSONArray("results").getJSONObject(3)
                        .getJSONArray("address_components").getJSONObject(1).getString("short_name");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return city;
    }

    public String generateValidAddress(Long businessId) {
        Address businessAddress = businessRepository.getById(businessId).getAddress();
        return businessAddress.getStreetNumber() + " " +
                businessAddress.getStreet() + " " + businessAddress.getCity();
    }


    public JSONObject getCoordinates(JSONObject userLocation) {
        JSONArray results = userLocation.getJSONArray("results");
        JSONObject dataObject = results.getJSONObject(0);
        JSONObject originGeometry = dataObject.getJSONObject("geometry");
        return originGeometry.getJSONObject("location");
    }

    public JSONObject getLocationInfo(String address) {
        JSONObject jsonObject = new JSONObject();

        try {
            address = address.replaceAll(" ", "%20");
            HttpPost httppost = new HttpPost("https://maps.google.com/maps/api/geocode/json?address=" + address + "&sensor=false&key=" + GOOGLE_API_KEY);
            HttpClient client = HttpClientBuilder.create().build();
            HttpResponse response = client.execute(httppost);
            HttpEntity entity = response.getEntity();
            InputStream stream = entity.getContent();
            StringBuilder stringBuilder = new StringBuilder();

            int b;
            while ((b = stream.read()) != -1) {
                stringBuilder.append((char) b);
            }
            jsonObject = new JSONObject(stringBuilder.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    public int findDistanceBetweenLocations(JSONObject originCoordinated, JSONObject destinationCoordinates) {

        double originLng = originCoordinated.getDouble("lng");
        double originLat = originCoordinated.getDouble("lat");
        double destinationLng = destinationCoordinates.getDouble("lng");
        double destinationLat = destinationCoordinates.getDouble("lat");

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();

        Request request = new Request.Builder()
                .url("https://maps.googleapis.com/maps/api/distancematrix/json?" +
                        "origins=" + originLat + "%2C" + originLng + "&" +
                        "destinations=" + destinationLat + "%2C" + destinationLng + "&key=" + GOOGLE_API_KEY)
                .method("GET", null)
                .build();

        Response response;
        int distance = 0;
        try {
            response = client.newCall(request).execute();

            if (response.body() != null) {
                String responseBodyString = Objects.requireNonNull(response.body()).string();
                JSONObject responseJSON = new JSONObject(responseBodyString);
                distance = responseJSON.getJSONArray("rows").getJSONObject(0).getJSONArray("elements")
                        .getJSONObject(0).getJSONObject("distance").getInt("value");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return distance;
    }
}
