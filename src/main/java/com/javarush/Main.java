package com.javarush;

import com.javarush.domain.*;
import com.javarush.dao.*;
import org.hibernate.SessionFactory;
import org.hibernate.Session;

import java.time.LocalDateTime;
import java.time.Year;
import java.util.HashSet;
import java.util.List;
import java.math.BigDecimal;
import java.util.Set;

public class Main {
    private final ActorDAO actorDAO;
    private final AddressDAO addressDAO;
    private final CategoryDAO categoryDAO;
    private final CityDAO cityDAO;
    private final CountryDAO countryDAO;
    private final CustomerDAO customerDAO;
    private final FilmDAO filmDAO;
    private final FilmTextDAO filmTextDAO;
    private final InventoryDAO inventoryDAO;
    private final LanguageDAO languageDAO;
    private final PaymentDAO paymentDAO;
    private final RentalDAO rentalDAO;
    private final StaffDAO staffDAO;
    private final StoreDAO storeDAO;

    private final SessionFactory sessionFactory;


    public Main() {

        sessionFactory = new FilmsRentSessionFactory().getSessionFactory();

        actorDAO = new ActorDAO(sessionFactory);
        addressDAO = new AddressDAO(sessionFactory);
        categoryDAO = new CategoryDAO(sessionFactory);
        cityDAO = new CityDAO(sessionFactory);
        countryDAO = new CountryDAO(sessionFactory);
        customerDAO = new CustomerDAO(sessionFactory);
        filmDAO = new FilmDAO(sessionFactory);
        filmTextDAO = new FilmTextDAO(sessionFactory);
        inventoryDAO = new InventoryDAO(sessionFactory);
        languageDAO = new LanguageDAO(sessionFactory);
        paymentDAO = new PaymentDAO(sessionFactory);
        rentalDAO = new RentalDAO(sessionFactory);
        staffDAO = new StaffDAO(sessionFactory);
        storeDAO = new StoreDAO(sessionFactory);

    }

    public static void main(String[] args) {
        Main main = new Main();
        Customer customer = main.createCustomer();
        main.customerReturnFilm();
        main.customerGetInventory(customer);
        main.createNewFilm();
    }

    private void createNewFilm() {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();

            Language language = languageDAO.getItems(0, 5).stream().unordered().findAny().get();
            List<Category> categories = categoryDAO.getItems(0, 5);
            List<Actor> actors = actorDAO.getItems(0, 5);


            Film film = new Film();
            film.setActors(new HashSet<>(actors));
            film.setCategorys(new HashSet<>(categories));
            film.setDescription("test Description");
            film.setLanguage(language);
            film.setSpecialFeatures(Set.of(Feature.TRAILERS, Feature.COMMENTARIES));
            film.setRentalDuration((byte) 3);
            film.setRentalRate(BigDecimal.valueOf(4.99));
            film.setLength((short) 120);
            film.setReplacementCost(BigDecimal.valueOf(19.99));
            film.setRating(Rating.valueOf("G"));
            film.setTitle("test film");
            film.setReleaseYear(Year.now());
            filmDAO.save(film);

            FilmText filmText = new FilmText();
            filmText.setId(film.getId());
            filmText.setFilm(film);
            filmText.setTitle("test title");
            filmText.setDescription("test text decription");
            filmTextDAO.save(filmText);

            session.getTransaction().commit();
        }
    }

    public void customerGetInventory(Customer customer) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            Store store = storeDAO.getItems(0, 1).get(0);

            Film film = filmDAO.getAvalableFilmForRent();
            Inventory inventory = new Inventory();
            inventory.setFilm(film);
            inventory.setStore(store);

            inventoryDAO.save(inventory);

            Staff staff = store.getManagerStaff();

            Rental rental = new Rental();
            rental.setCustomerId(customer);
            rental.setInventoryId(inventory);
            rental.setStaff(staff);
            rental.setRentalDate(LocalDateTime.now());
            rentalDAO.save(rental);

            Payment payment = new Payment();
            payment.setAmount(BigDecimal.valueOf(14.05));
            payment.setCustomerId(customer);
            payment.setRentalId(rental);
            payment.setPaymentDate(LocalDateTime.now());
            payment.setStaff(staff);
            paymentDAO.save(payment);

            session.getTransaction().commit();
        }
    }

    public void customerReturnFilm() {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();

            Rental rental = rentalDAO.getAnyUnreturnedRental();
            rental.setReturnDate(LocalDateTime.now());
            rentalDAO.save(rental);

            session.getTransaction().commit();
        }
    }

    private Customer createCustomer() {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            Store store = storeDAO.getItems(0, 1).get(0);
            City city = cityDAO.getByName("London");
            Address address = new Address();
            address.setAddress("Street");
            address.setPhone("+8777777777");
            address.setCity(city);
            address.setDistrict("District");
            addressDAO.save(address);

            Customer customer = new Customer();
            customer.setActive(true);
            customer.setFirstName("John");
            customer.setLastName("Doe");
            customer.setEmail("qwe@qwe.ru");
            customer.setAddress(address);
            customer.setStore(store);
            customerDAO.save(customer);

            session.getTransaction().commit();
            return customer;

        }
    }
}