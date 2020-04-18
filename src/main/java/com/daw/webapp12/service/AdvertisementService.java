package com.daw.webapp12.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.daw.webapp12.entity.Advertisement;
import com.daw.webapp12.entity.Search;
import com.daw.webapp12.entity.Users;
import com.daw.webapp12.repository.AdvertisementRepository;

import com.daw.webapp12.security.UserComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AdvertisementService implements AdvertisementInterface{

    @Autowired
    private AdvertisementRepository advertisementRepository;

    @Autowired
    private UserService userService;

	@Autowired
	private UserComponent userComponent;

    public List<Advertisement> findAll(){
        return advertisementRepository.findAll();
    }

	public List<Entry<String, Integer>> graphValues() {
		List<Advertisement> allAdvertisements = this.findAll();

		HashMap<String, Integer> mostCommonLocations = new HashMap<String, Integer>();
			for(int i = 0; i< allAdvertisements.size();i++){
				String auxLocation = allAdvertisements.get(i).getlocation();
				if(mostCommonLocations.containsKey(auxLocation)){
					int value = mostCommonLocations.get(auxLocation);
					mostCommonLocations.replace(auxLocation,value+1);
				}else{
					mostCommonLocations.put(auxLocation, 1);
				}
			}

			List<Map.Entry<String, Integer> > list = 
			new LinkedList<Map.Entry<String, Integer> >(mostCommonLocations.entrySet()); 

	 Collections.sort(list, new Comparator<Map.Entry<String, Integer> >() { 
		 public int compare(Map.Entry<String, Integer> o1,  
							Map.Entry<String, Integer> o2) 
		 { 
			 return (o2.getValue()).compareTo(o1.getValue());
		 } 
	 }); 
	   for(int i = 5;i<list.size();i++){
		list.remove(i);
       }
       return list;
    }

    public void recommendeds(List<Advertisement> recommendeds) {
        
		List<Advertisement> auxAdvertisements = new ArrayList<Advertisement>();
		List<Advertisement> ads = new ArrayList<Advertisement>();
		List<Advertisement> ads2 = new ArrayList<Advertisement>();
		List<Search> searches = userService.findByName("Angel").get().getMySearches();
		HashMap<Double,Integer> scores = new HashMap<Double,Integer>();
		
		List<String> typeOfSearches = new ArrayList<String>();
		int roomMean = 0;
		int bathroomMean = 0;
		int squareMetersMean = 0;
		List<String> locationsList = new ArrayList<String>();
		double sellPriceMean = 0;
		int numSells = 0;
		int numRents = 0;
		double rentPriceMean = 0;
		double score = 0;
		String typeOfRecommendation;
		String lastTwoSearches ="";

		int limitRent = 10;
		int limitSell = 10;

		String firstSearch = searches.get(searches.size()-1).getType();
		String secondSearch = searches.get(searches.size()-2).getType();
		String lastLocationSearched = searches.get(searches.size()-1).getlocation();
		String secondLocationSearched = searches.get(searches.size()-2).getlocation();

		if(firstSearch.equals("Alquiler") && secondSearch.equals("Alquiler")){
			lastTwoSearches = "Alquiler";
		}else if(firstSearch.equals("Venta") && secondSearch.equals("Venta")){
			lastTwoSearches = "Venta";
		}
		for(int i = 0;i<searches.size();i++){
			
			Search auxSearch = searches.get(i);
			roomMean += auxSearch.getrooms();
			bathroomMean += auxSearch.getbathrooms();
			squareMetersMean += auxSearch.getsquareMeters();
			locationsList.add(auxSearch.getlocation());
			if(auxSearch.getType().equals("Alquiler")){
				rentPriceMean += auxSearch.getprice();
				numRents = numRents+1;
			}else{
				sellPriceMean += auxSearch.getprice();
				numSells = numSells+1;	
			}
			
			if(searches.get(i).getType().equals("Alquiler") && !typeOfSearches.contains("Alquiler")){
				typeOfSearches.add("Alquiler");
			}else if(searches.get(i).getType().equals("Venta") && !typeOfSearches.contains("Venta")){
				typeOfSearches.add("Venta");
			}
		}

		if(typeOfSearches.contains("Alquiler") && typeOfSearches.contains("Venta")){
			typeOfRecommendation = "Both";
		}else if(typeOfSearches.contains("Alquiler") && !typeOfSearches.contains("Venta")){
			typeOfRecommendation = "Alquiler";
		}else{
			typeOfRecommendation = "Venta";
		}

		roomMean = roomMean / searches.size();
		bathroomMean = bathroomMean / searches.size();
		squareMetersMean = squareMetersMean / searches.size();
		rentPriceMean = rentPriceMean / numRents;
		sellPriceMean = sellPriceMean / numSells;

		int sellPriceMeanInt = (int) Math.round(sellPriceMean);
		int rentPriceMeanInt = (int) Math.round(rentPriceMean);

		if(typeOfRecommendation.equals("Alquiler")){
			ads = advertisementRepository.findPreferences(squareMetersMean-10, typeOfRecommendation, bathroomMean-1, lastLocationSearched, rentPriceMeanInt+100, roomMean-1, limitRent);
		}else if(typeOfRecommendation.equals("Venta")){
			ads = advertisementRepository.findPreferences(squareMetersMean-10, typeOfRecommendation, bathroomMean-1, lastLocationSearched, sellPriceMeanInt+10000, roomMean-1,limitSell);
		}else{
			if(firstSearch.equals("Alquiler")){
				limitRent=7;
				limitSell=3;
			}else{
				limitRent=3;
				limitSell=7;
			}
			ads = advertisementRepository.findPreferences(squareMetersMean-10, "Alquiler", bathroomMean-1, lastLocationSearched,rentPriceMeanInt+100, roomMean-1,limitRent);
			ads2 = advertisementRepository.findPreferences(squareMetersMean-10, "Venta", bathroomMean-1, lastLocationSearched, sellPriceMeanInt+10000, roomMean-1,limitSell);
			for(int i = 0;i<ads2.size();i++){
				ads.add(ads2.get(i));
			}
		}
		
		for(int i = 0;i<ads.size();i++){
			auxAdvertisements.add(ads.get(i));
		}

		if(!typeOfRecommendation.equals("Both")){
			for(int i = 0;i<auxAdvertisements.size();i++){
				Advertisement auxAd = auxAdvertisements.get(i);
				if(typeOfRecommendation.equals("Alquiler") && auxAd.gettype().equals("Venta")){
					auxAdvertisements.remove(i);
					i= i-1;
				}else if(typeOfRecommendation.equals("Venta") && auxAd.gettype().equals("Alquiler")){
					auxAdvertisements.remove(i);
					i= i-1;
				}
			}
		}
		
		

		for(int i = 0;i<auxAdvertisements.size();i++){
			Advertisement auxAd = auxAdvertisements.get(i);
			if(auxAd.getrooms() - roomMean ==0){
				score+= 2;
			}else{
				score+= (auxAd.getrooms() - roomMean) *2;
			}
			
			if(auxAd.getbathrooms() - bathroomMean ==0){
				score+= 2;
			}else{
				score+= (auxAd.getbathrooms() - bathroomMean) *2;
			}
			
			if(auxAd.getsquareMeters() - squareMetersMean ==0){
				score+= 2;
			}else{
				score+= ((auxAd.getsquareMeters() - squareMetersMean)/2) *2;
			}

			if(locationsList.contains(auxAd.getlocation())){
			 	score+=15;
			}

			if(auxAd.gettype().equals("Alquiler")){
				if(rentPriceMean - auxAd.getprice()>0){
					score+= ((rentPriceMean - auxAd.getprice())/50) *4;
				}
				if(lastTwoSearches.equals("Alquiler")){
					score = score *2.5;
				}else if(firstSearch.equals("Alquiler")){
					score = score *1.5;
				}
			}else if(auxAd.gettype().equals("Venta")){
					score+= ((sellPriceMean - auxAd.getprice())/5000) *2;
					if(lastTwoSearches.equals("Venta")){
						score = score * 2.25;
					}else if(firstSearch.equals("Venta")){
						score = score *1.2;
					}
			}
			scores.put(score, i);
			score = 0;
		}
			List<Double> mapKeys = new ArrayList<>(scores.keySet());
			Collections.sort(mapKeys);
			if(ads.size()==1){
				recommendeds.add(auxAdvertisements.get(scores.get(mapKeys.get(mapKeys.size()-1))));
			}else if(ads.size()==2){
				recommendeds.add(auxAdvertisements.get(scores.get(mapKeys.get(mapKeys.size()-1))));
				recommendeds.add(auxAdvertisements.get(scores.get(mapKeys.get(mapKeys.size()-2))));
			}else{
				recommendeds.add(auxAdvertisements.get(scores.get(mapKeys.get(mapKeys.size()-1))));
				recommendeds.add(auxAdvertisements.get(scores.get(mapKeys.get(mapKeys.size()-2))));
				recommendeds.add(auxAdvertisements.get(scores.get(mapKeys.get(mapKeys.size()-3))));
			}
    }

    @Override
    public Advertisement addAdvertisement(Advertisement advertisement) {
        return advertisementRepository.save(advertisement);
    }

    @Override
    public void deleteAdvertisement(Long id) {
        advertisementRepository.deleteById(id);
    }

    @Override
    public Advertisement findById(Long id) {
        return advertisementRepository.findById(id).orElse(null);
    }
    @Override
    public List<Advertisement> findByLocation(String location) {
        return advertisementRepository.findByLocation(location);
    }


}