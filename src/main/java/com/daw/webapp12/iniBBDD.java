package com.daw.webapp12;

import com.daw.webapp12.entity.*;
import com.daw.webapp12.entity.Users;
import com.daw.webapp12.repository.AdvertisementRepository;
import com.daw.webapp12.repository.BlogRepository;
import com.daw.webapp12.repository.CommentRepository;
import com.daw.webapp12.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class iniBBDD {

    @Autowired
    UserRepository userRepository;
    @Autowired
    AdvertisementRepository anuncioRepository;
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    BlogRepository blogRepository;

    @PostConstruct
    public void init(){

        
        Users users2 = new Users("Edu", "edu@gmail.com","12345678","ROLE_USER");
        userRepository.save(users2);
        Users admin = new Users("admin", "admin@gmail.com","admin","ROLE_ADMIN");
        userRepository.save(admin);
        Users users3 = new Users("Karol","karol@gmail.com","12345678","ROLE_ADMIN");
        userRepository.save(users3);
        Users users4 = new Users("Sebastian","sebastian@gmail.com","12345678","ROLE_USER");
        userRepository.save(users4);
        //Users users5 = new Users("Maria","maria@gmail.com","12345678");
        //userRepository.save(users5);
  
        //Advertisement(String tipo, String vivienda, Integer habitaciones, Integer baños, String metros2, String localizacion, String direccion, double precio)
        Advertisement anun1 = new Advertisement("Venta","Casa",(Integer)4,(Integer)2,120,"Madrid","calle azul,2",(double)200000);
        Comment coment1= new Comment("Hola, me ha encantado");
        Users users5 = new Users("Maria","maria@gmail.com","12345678","ROLE_ADMIN");
        userRepository.save(users5);
        coment1.setAuthor(users5.getName());
        commentRepository.save(coment1);
        anun1.getImages().add("work-1.jpg");
        anun1.getComments().add(coment1);
        anuncioRepository.save(anun1);
        Advertisement anun2 = new Advertisement("Venta","Local",(Integer)2,(Integer)1,50,"Madrid","calle verde,3",(double)120000);
        anun2.getImages().add("work-7.jpg");
        anuncioRepository.save(anun2);
        Advertisement anun3 = new Advertisement("Alquiler","Piso",(Integer)3,(Integer)1,90,"Pontevedra","calle carlos v,4,2 C",(double)1600);
        anun3.getImages().add("work-3.jpg");
        anuncioRepository.save(anun3);
        Advertisement anun4 = new Advertisement("Venta","Casa",(Integer)2,(Integer)1,56,"Madrid","calle verde,3",(double)78990);
        anun4.getImages().add("work-5.jpg");
        anuncioRepository.save(anun4);
        Advertisement anun5 = new Advertisement("Alquiler","Local",(Integer)1,(Integer)1,78,"Madrid","calle verde,3",(double)1200);
        anun5.getImages().add("work-4.jpg");
        anuncioRepository.save(anun5);
        Advertisement anun6 = new Advertisement("Venta","Local",(Integer)4,(Integer)2,85,"Mostoles","calle verde,3",(double)140000);
        anun6.getImages().add("work-7.jpg");
        anuncioRepository.save(anun6);
        Advertisement anun7 = new Advertisement("Alquiler","Local",(Integer)3,(Integer)1,78,"Mostoles","calle verde,3",(double)1200);
        anun7.getImages().add("work-6.jpg");
        anuncioRepository.save(anun7);
        Advertisement anun8 = new Advertisement("Alquiler","Local",(Integer)1,(Integer)1,78,"Valencia","calle verde,3",(double)1000);
        anun8.getImages().add("work-7.jpg");
        anuncioRepository.save(anun8);
        Advertisement anun9 = new Advertisement("Alquiler","Local",(Integer)3,(Integer)1,78,"Ciudad Real","calle verde,3",(double)650);
        anun9.getImages().add("work-2.jpg");
        anuncioRepository.save(anun9);
        Advertisement anun10 = new Advertisement("Alquiler","Local",(Integer)3,(Integer)1,78,"Ciudad Real","calle verde,3",(double)650);
        anun10.getImages().add("work-2.jpg");
        anuncioRepository.save(anun10);
        Advertisement anun11 = new Advertisement("Alquiler","Piso",(Integer)3,(Integer)1,95,"Madrid","calle tulipan,5",(double)650);
        anun11.getImages().add("work-2.jpg");
        anuncioRepository.save(anun11);
        Advertisement anun12 = new Advertisement("Alquiler","Piso",(Integer)2,(Integer)1,64,"Mostoles","calle caqui,4",(double)600);
        anun12.getImages().add("work-2.jpg");
        anuncioRepository.save(anun12);
        Advertisement anun13 = new Advertisement("Alquiler","Piso",(Integer)3,(Integer)1,78,"Mostoles","calle verde,4",(double)900);
        anun13.getImages().add("work-2.jpg");
        anuncioRepository.save(anun13);
        Advertisement anun14 = new Advertisement("Venta","Casa",(Integer)3,(Integer)1,92,"Mostoles","calle Empecinado,9",(double)750);
        anun14.getImages().add("work-2.jpg");
        anuncioRepository.save(anun14);
        Advertisement anun15 = new Advertisement("Venta","Casa",(Integer)1,(Integer)1,65,"Madrid","calle verde,3",(double)550);
        anun15.getImages().add("work-2.jpg");
        anuncioRepository.save(anun15);
        Advertisement anun16 = new Advertisement("Alquiler","Piso",(Integer)2,(Integer)1,78,"Madrid","calle verde,3",(double)700);
        anun16.getImages().add("work-2.jpg");
        anuncioRepository.save(anun16);
        Advertisement anun17 = new Advertisement("Alquiler","Piso",(Integer)2,(Integer)1,62,"Madrid","calle verde,3",(double)650);
        anun17.getImages().add("work-2.jpg");
        anuncioRepository.save(anun17);
        Advertisement anun18 = new Advertisement("Alquiler","Piso",(Integer)2,(Integer)1,75,"Madrid","calle verde,3",(double)600);
        anun18.getImages().add("work-2.jpg");
        anuncioRepository.save(anun18);
        Advertisement anun19 = new Advertisement("Alquiler","Piso",(Integer)2,(Integer)1,90,"Madrid","calle verde,3",(double)580);
        anun19.getImages().add("work-2.jpg");
        anuncioRepository.save(anun19);
        Advertisement anun20 = new Advertisement("Alquiler","Piso",(Integer)2,(Integer)1,72,"Madrid","calle verde,3",(double)60);
        anun20.getImages().add("work-2.jpg");
        anuncioRepository.save(anun20);
        Advertisement anun21 = new Advertisement("Alquiler","Piso",(Integer)2,(Integer)1,67,"Madrid","calle verde,3",(double)620);
        anun21.getImages().add("work-2.jpg");
        anuncioRepository.save(anun21);
        Advertisement anun22 = new Advertisement("Alquiler","Piso",(Integer)2,(Integer)1,78,"Mostoles","calle verde,3",(double)700);
        anun22.getImages().add("work-2.jpg");
        anuncioRepository.save(anun22);
        Advertisement anun23 = new Advertisement("Alquiler","Piso",(Integer)2,(Integer)1,62,"Mostoles","calle verde,3",(double)650);
        anun23.getImages().add("work-2.jpg");
        anuncioRepository.save(anun23);
        Advertisement anun24 = new Advertisement("Alquiler","Piso",(Integer)2,(Integer)1,75,"Mostoles","calle verde,3",(double)600);
        anun24.getImages().add("work-2.jpg");
        anuncioRepository.save(anun24);
        Advertisement anun25 = new Advertisement("Alquiler","Piso",(Integer)2,(Integer)1,90,"Mostoles","calle verde,3",(double)580);
        anun25.getImages().add("work-2.jpg");
        anuncioRepository.save(anun25);
        Advertisement anun26 = new Advertisement("Alquiler","Piso",(Integer)2,(Integer)1,72,"Mostoles","calle verde,3",(double)60);
        anun26.getImages().add("work-2.jpg");
        anuncioRepository.save(anun26);
        Advertisement anun27 = new Advertisement("Alquiler","Piso",(Integer)2,(Integer)1,67,"Mostoles","calle verde,3",(double)620);
        anun27.getImages().add("work-2.jpg");
        anuncioRepository.save(anun27);


        Search search1 = new Search("Alquiler",(Integer)2,(Integer)1,40,"Madrid",(double)1200);
        Search search2 = new Search("Venta",(Integer)2,(Integer)2,50,"Mostoles",(double)120000);
        Search search4 = new Search("Venta",(Integer)3,(Integer)3,65,"Mostoles",(double)120000);
        Search search5 = new Search("Venta",(Integer)2,(Integer)2,70,"Madrid",(double)120000);


        Users users1 = new Users("Angel","angel@gmail.com","12345678","ROLE_USER");
        users1.getMyFavourites().add(anun1);
        users1.getMyFavourites().add(anun2);
        users1.getMyFavourites().add(anun3);
        users1.getMyAdvertisements().add(anun4);
        users1.getMyAdvertisements().add(anun6);
        users1.getMyAdvertisements().add(anun7);

        /*users1.getMyAdvertisements().add(anun4);
        users1.getMyAdvertisements().add(anun5);
        users1.getMyAdvertisements().add(anun6);*/

        users1.getMySearches().add(search1);
        users1.getMySearches().add(search2);
        users1.getMySearches().add(search4);
        users1.getMySearches().add(search5);


        userRepository.save(users1);
           

        Blog blog1 = new Blog("CALIDEZ Y CARÁCTER SE ENCUENTRAN FRENTE A FRENTE EN ESTA CASA", "Los propietarios de esta casa unifamiliar en una población cercana a Barcelona —una pareja con hijos adolescentes— querían reformarla para adaptarla a los tiempos modernos. Solo querían un lavado de cara y que les ayudáramos a escoger mobiliario y textiles para modernizar la vivienda.");
        blog1.getImages().add("image_2.jpg");
        blogRepository.save(blog1);

        Blog blog2 = new Blog("TENDENCIAS EN COCINAS QUE TE VOLVERÁN CRAZY ESTE 2020", "VERDE NATURAL La preocupación por la sostenibilidad ha hecho que los tonos verdes más naturales se cuelen en nuestras cocinas durante este 2020. ACENTOS TURQUESA Los tonos turquesa no solo aportan luminosidad a la cocina, sino también mucha frescura. Además, resaltan un montón con otros colores de base como el blanco, y siempre se ven muy limpios. Ideales para armarios y azulejos.");        
        blog2.getImages().add("image_3.jpg");
        blogRepository.save(blog2);

        Blog blog3 = new Blog("Piscinas en tu propia casa", "Acercate y prueba");
        blog3.getImages().add("image_4.jpg");        
        blogRepository.save(blog3);

        Blog blog4 = new Blog("¿Madrid es caro?", "No si alquilas con estas inmobiliarias");
        blog4.getImages().add("image_5.jpg");       
        blogRepository.save(blog4);

        Blog blog5 = new Blog("Trucos para conseguir el mejor alquiler", "Aprende ya");
        blog5.getImages().add("image_3.jpg");        
        blogRepository.save(blog5);

        Blog blog6 = new Blog("Alquilar o comprar", "Cual es la mejor decision para ti");
        blog6.getImages().add("image_5.jpg");       
        blogRepository.save(blog6);

        Blog blog7 = new Blog("Descubre ya tu apartamento ideal", "Primera linea de playa");
        blog7.getImages().add("image_6.jpg");        
        blogRepository.save(blog7);

        Blog blog8 = new Blog("Casas de hasta tres plantas", "Por solo 1000 euros al mes");
        blog8.getImages().add("image_2.jpg");       
        blogRepository.save(blog8);

        Blog blog9= new Blog("La revolucion de los alquileres  ha llegado", "Entérate"); 
        blog9.getImages().add("image_4.jpg");       
        blogRepository.save(blog9);
    }
}