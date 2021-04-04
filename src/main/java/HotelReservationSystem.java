//https://www3.ntu.edu.sg/home/ehchua/programming/java/JavaEnum.html
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class HotelReservationSystem {
    ArrayList<Hotel> hotelList;

    public HotelReservationSystem() {
        this.hotelList = new ArrayList<>();
    }

    public void addHotel(Hotel hotelToAdd){
        this.hotelList.add(hotelToAdd);
    }

    public Hotel getHotelByName(String hotelNameToSearchFor){
        Hotel hotelToSearchFor = new Hotel();
        for (Hotel hotel : this.hotelList) {
            if(hotel.hotelName.equals(hotelNameToSearchFor)){
                return hotel;
            }
        }
        System.out.println("Hotel with name " + hotelNameToSearchFor + "is not available in our records.");
        return null;
    }

    public Result showCheapestThreeRateForADateRangeReturnCheapest(CustomerType customerType, LocalDate startDate , LocalDate endDate){
        ArrayList<Result> allHotelRateList= this.getAllRateForADateRange(customerType,startDate,endDate);
        //System.out.println("You are eligible for " + customerType + " Rates.");
        ArrayList<Result> top3HotelRateList= (ArrayList<Result>) allHotelRateList.stream()
                                                                                 //.sorted((resultOne,resultTwo) -> resultOne.getTotalCalculatedRate() - resultTwo.getTotalCalculatedRate())
                                                                                 .sorted(new ResultComparator())
                                                                                 .limit(3)
                                                                                 .collect(Collectors.toList());
        //System.out.println(top3HotelRateList);
        return top3HotelRateList.get(0);
    }

    private ArrayList<Result> getAllRateForADateRange(CustomerType customerType, LocalDate startDate , LocalDate endDate){
        return (ArrayList<Result>) hotelList.stream()
                                            .map(hotel -> {
                                                Result newResult = new Result();
                                                newResult.setHotelName(hotel.hotelName);
                                                newResult.setHotelRating(hotel.hotelRating);
                                                newResult.setTotalCalculatedRate(hotel.getRateForADateRange(customerType, startDate, endDate));
                                                return newResult;
                                            })
                                            .collect(Collectors.toList());
    }
}
