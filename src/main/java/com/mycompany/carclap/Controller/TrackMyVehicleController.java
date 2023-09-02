package com.mycompany.carclap.Controller;

import com.mycompany.carclap.models.LocationTracker;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class TrackMyVehicleController {

    @Scheduled(fixedDelay = 2000)
    @GetMapping("/location")
    public RedirectView getLocation(){
        //HttpServletRequest request = ((ServletRequestAttributes) (Objects.requireNonNull(RequestContextHolder.getRequestAttributes()))).getRequest();
        //String ip = Optional.ofNullable(request.getHeader("X-FORWARDED-FOR")).orElse(request.getRemoteAddr());
        /*String url = "http://ip-api.com/json/" + ip;
        RestTemplate restTemplate = new RestTemplate();
        LocationTracker locationTracker = restTemplate.getForObject(url, LocationTracker.class);
        LocationTracker tracker = new LocationTracker(locationTracker.getLatitude(), locationTracker.getLongitude());
        tracker.trackLocation();*/
        RedirectView redirectView = new RedirectView();
       // redirectView.setUrl("https://www.google.com/maps/dir/?api=1&origin="+locationTracker.getLatitude()+"%2C"+ locationTracker.getLongitude()+"&destination= Bestech business Towers");
        redirectView.setUrl("https://www.google.com/maps/dir/?api=1"+"&destination= Guru Nanak Dev University,Amritsar");
        return redirectView;
    }
}
