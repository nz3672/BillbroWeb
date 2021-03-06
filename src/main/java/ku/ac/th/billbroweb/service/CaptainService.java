package ku.ac.th.billbroweb.service;

import ku.ac.th.billbroweb.data.CaptainRepository;
import ku.ac.th.billbroweb.model.Captain;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CaptainService {
    @Autowired
    private CaptainRepository repository;
    @Autowired
    private RestTemplate restTemplate;

    private String hash(String pin){
        String salt = BCrypt.gensalt(12);
        return BCrypt.hashpw(pin, salt); // process hash
    }

    public CaptainService() {
    }

    public CaptainService(CaptainRepository repository, RestTemplate restTemplate) {
        this.repository = repository;
        this.restTemplate = restTemplate;
    }


    public void createCaptain(Captain captain){
        String hashPin = hash(captain.getC_pwd());
        captain.setC_pwd(hashPin);
        repository.save(captain);
    }

    public List<Captain> getCaptain(){
        return repository.findAll();
    }

    public  Captain findCaptain(String username) {
        try {
            return repository.findBycUsername(username).get();
        } catch (NoSuchElementException e ){
            return null;
        }
    }

    public Captain checkPin(Captain inputCaptain){
        Captain storedCaptain = findCaptain(inputCaptain.getcUsername());
        if (storedCaptain != null) {
            String storedPin = storedCaptain.getC_pwd();
            if (BCrypt.checkpw(inputCaptain.getC_pwd(), storedPin)){
                return storedCaptain;
            }
        }
        return null;
    }

    public void editCaptain(Captain captain){
        String url = "http://localhost:8090/api/captain/" + captain.getC_id();
        restTemplate.put(url,captain);
    }

}
