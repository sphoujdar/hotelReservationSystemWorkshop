//https://www3.ntu.edu.sg/home/ehchua/programming/java/JavaEnum.html
import java.util.ArrayList;

public class HotelReservationSystem {
    ArrayList<Hotel> hotelList;

    public HotelReservationSystem() {
        this.hotelList = new ArrayList<Hotel>();
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
}
