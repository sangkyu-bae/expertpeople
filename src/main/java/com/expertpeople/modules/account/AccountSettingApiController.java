package com.expertpeople.modules.account;

import com.expertpeople.modules.account.form.Profile;
import com.expertpeople.modules.zone.Zone;
import com.expertpeople.modules.zone.ZoneRepository;
import com.expertpeople.modules.zone.ZoneService;
import com.expertpeople.modules.zone.form.ZoneForm;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.io.JsonEOFException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AccountSettingApiController {

    private final AccountService accountService;
    private final ZoneRepository zoneRepository;

    @PostMapping("/setting/profile")
    public ResponseEntity<?>updateProfile(@CurrentAccount Account account,@RequestBody @Valid Profile profile,
                                          Errors errors)throws JsonProcessingException{
        if(errors.hasErrors()){
            return ResponseEntity.badRequest().build();
        }
        accountService.updateAccountProfile(account,profile);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/zone")
    public ResponseEntity<?> getZoneTag(@CurrentAccount Account account) throws JsonProcessingException{
        Map<String,Object> zone =new HashMap<>();

        Set<Zone> zones =accountService.getZone(account);
        List<String> allZone=zoneRepository.findAll().stream().map(Zone::toString).collect(Collectors.toList());

        return ResponseEntity.ok().body(new ZoneResult<>(zones,allZone));
    }


    @PostMapping("/zone/add")
    public ResponseEntity<?>addZoneTag(@CurrentAccount Account account, @RequestBody ZoneForm zoneForm)throws JsonProcessingException {
        Zone zone=zoneRepository.findByCityAndProvince(zoneForm.getCityName(),zoneForm.getProvinceName());
        if(zone==null){
            return ResponseEntity.badRequest().build();
        }
        accountService.addZone(account,zone);

        return ResponseEntity.ok().build();
    }

    @Getter
    @Setter
    static class ZoneResult<T>{
        private T zone;
        private T allZone;

        public ZoneResult(T zones, T allZone) {
            this.zone=zones;
            this.allZone=allZone;
        }
    }
}
