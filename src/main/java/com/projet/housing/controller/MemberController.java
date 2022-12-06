package com.projet.housing.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.projet.housing.db.MemberRepository;
import com.projet.housing.dto.ApiError;
import com.projet.housing.dto.MemberDTO;
import com.projet.housing.helper.FileUtil;
import com.projet.housing.model.Member;
import com.projet.housing.model.Minister;
import com.projet.housing.service.MemberService;
import com.projet.housing.service.MinisterService;
import com.projet.housing.service.ReportService;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;

@RestController
// @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
@RequestMapping("/api/manager")
@CrossOrigin
public class MemberController {
    @Autowired
    private MemberRepository mRepository;

    @Autowired
    private MemberService memberService;

    @Autowired
    private MinisterService ministerService;

    @Autowired
    private Environment environment;

    @Autowired
    private ReportService reportService;

    /**
     * Create - Add a new member
     *
     * @param member An object member
     * @return The member object saved
     * @throws IOException
     */
    // @PreAuthorize("hasAuthority('PM_ADD_ME')")
    @PostMapping("/save-member")
    public Object createMember(@Valid @RequestBody MemberDTO member) throws IOException {
        Optional<Member> us = mRepository.checkIfMemberExistByNomAndPrenom(member.getNom(), member.getPrenom());
        if (us.isPresent()) {
            final ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR,
                    environment.getProperty("unique.membername"), environment.getProperty("unique.membername"));
            return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
        } else {

            // String realName = member.getNom().concat(member.getPrenom());
            // byte[] decodedBytes = Base64.getDecoder().decode(member.getPhoto().split(",")[1]);

            // String ext = member.getPhoto().split(";")[0].split("/")[1];

            // FileUtils.writeByteArrayToFile(
            //         new File(resourceLoader.getResource("/upload-file/").getURL() + realName.concat(".").concat(ext)),
            //         decodedBytes, true);
            String photoName = base64ToImage(member);
            Optional<Minister> minister = ministerService.getMinister(member.getMinistere());

            Member m = new Member();
            m.setNom(member.getNom());
            m.setPrenom(member.getPrenom());
            m.setSexe(member.getSexe());
            m.setTelephone(member.getTelephone());
            m.setDateDeNaissance(member.getDateDeNaissance());
            m.setAdresse(member.getAdresse());
            m.setMinistere(minister.get());
            m.setPhoto(photoName);
            return memberService.saveMember(m);
        }
    }

    /**
     * @param member
     * @return
     * @throws IOException
     */
    private String base64ToImage(MemberDTO member) throws IOException {
        byte[] decodedBytes = Base64.getDecoder().decode(member.getPhoto().split(",")[1]);
        
        String ext = member.getPhoto().split(";")[0].split("/")[1];
        String realName = member.getNom().concat(member.getPrenom()).concat(".").concat(ext);
        
        // String directory = servletContext.getRealPath("/")+"upload-file";
        // new FileOutputStream(directory).write(decodedBytes);
        System.out.println("chemin =>"+Paths.get("./upload-file").toAbsolutePath().normalize().toString());
        
        FileUtils.writeByteArrayToFile(
                new File(Paths.get("./upload-file/").toAbsolutePath().normalize().toString(), realName),
                decodedBytes, true);
        return realName;
    }

    /**
     * @param fileName
     * @return
     */
    private boolean deleteFile(String fileName){
        try {
            Files.delete(Paths.get("/.upload-file/" + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    @GetMapping("/view/{fileCode}")
    public ResponseEntity<?> view(@PathVariable("fileCode") String fileCode) {

        Resource resource = null;
        try {
            resource = FileUtil.getFileAsResource(fileCode);
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        }

        if (resource == null) {
            return new ResponseEntity<>("File not found", HttpStatus.NOT_FOUND);
        }

        String contentType = "image/png";
        String headerValue = "inline; filename=\"" + resource.getFilename() + "\"";

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
                .body(resource);
    }

    // https://www.techgeeknext.com/install-jasper-studio
    // resourceLocation = "classpath:employees-details.jrxml"
    @GetMapping("/report-liste-membre")
    public ResponseEntity<?> viewReportAllMember() {
        try{
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("createdBy", "David");
            parameters.put("createdAt", "David");

            JasperPrint report = reportService.getJasperPrint(memberService.listMember(), "classpath:liste-des-membres.jrxml", parameters);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_PDF);
            httpHeaders.setContentDispositionFormData("filename", "liste_des_membres.pdf");
    
            return new ResponseEntity<byte[]>(
                JasperExportManager.exportReportToPdf(report), httpHeaders, HttpStatus.OK);
        }catch(Exception ex){
            return new ResponseEntity<>("File not found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/downloadFile/{fileCode}")
    public ResponseEntity<?> downloadFile(@PathVariable("fileCode") String fileCode) {

        Resource resource = null;
        try {
            resource = FileUtil.getFileAsResource(fileCode);
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        }

        if (resource == null) {
            return new ResponseEntity<>("File not found", HttpStatus.NOT_FOUND);
        }

        String contentType = "application/octet-stream";
        String headerValue = "attachment; filename=\"" + resource.getFilename() + "\"";

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
                .body(resource);
    }

    /**
     * Read - Get one member
     *
     * @param id The id of the member
     * @return An Member object full filled
     */
    @GetMapping("/member/{id}")
    public Member getMember(@PathVariable("id") final String id) {
        Optional<Member> member = memberService.getMember(id);
        if (member.isPresent()) {
            return member.get();
        } else {
            return null;
        }
    }

    /**
     * Read - Get all members
     *
     * @return - An Iterable object of Member full filled
     */
    @GetMapping("/members")
    public Iterable<Member> getMembers() {
        return memberService.getMembers();
    }

    /**
     * retourne la liste des membres ordonnés avec pagination 
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/members-list")
    public Page<Member> getMembers(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "3") int size) {
        // Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
        //         : Sort.by(sortBy).descending();
        Pageable paging = PageRequest.of(page, size, Sort.by("createdDate").descending().and(Sort.by("lastModifiedDate").descending()));
        return memberService.getMembers(paging);
    }

    /**
     * retourne la liste des membres en fonction du mot clé rechercher
     * @param search
     * @param page
     * @param size
     * @return
     */
    
    @GetMapping("/search-members-list/{search}")
    public Page<Member> getSearchMembers(@PathVariable("search") final String search, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "3") int size) {
        Pageable paging = PageRequest.of(page, size);
        return memberService.getSearchMembers(search, paging);
    }

    /**
     * retourne la liste des membres en fonction du mot clé rechercher, ministere, sexe
     * @param search
     * @param page
     * @param size
     * @param sexe
     * @param minister
     * @return
     */
    @GetMapping(value = {"/search-multi", "/search-multi/{search}"})
    public Page<Member> getSearchMultiCriteriaMembers(@PathVariable(name = "search", required = false) final String search, @RequestParam(defaultValue = "0") int page, 
    @RequestParam(defaultValue = "3") int size, @RequestParam(defaultValue = "") String sexe, @RequestParam(defaultValue = "") String minister) {
        Pageable paging = PageRequest.of(page, size);
        return memberService.getSearchMembersMultiCriteria(search, sexe, minister, paging);
    }
    /**
     * Update - Update an existing member
     *
     * @param id     - The id of the member to update
     * @param member - The member object updated
     * @return
     * @throws IOException
     */
    // @PreAuthorize("hasAuthority('PM_EDI_ME')")
    @PutMapping("/edit-member/{id}")
    public Member updateMember(@PathVariable("id") final String id, @Valid @RequestBody MemberDTO member) throws IOException {
        Optional<Member> e = memberService.getMember(id);
        if (e.isPresent()) {
            Member currentMember = e.get();
            currentMember.setLastModifiedDate(new Date());

            String membername = member.getNom();
            if (membername != null) {
                currentMember.setNom(membername);
            }
            String prenom = member.getPrenom();
            if (prenom != null) {
                currentMember.setPrenom(prenom);
            }
            String sexe = member.getSexe();
            if (sexe != null) {
                currentMember.setSexe(sexe);
            }
            String dateNaiss = member.getDateDeNaissance();
            if (dateNaiss != null) {
                currentMember.setDateDeNaissance(dateNaiss);
            }
            String adress = member.getAdresse();
            if (adress != null) {
                currentMember.setAdresse(adress);
            }
            String tel = member.getTelephone();
            if (tel != null) {
                currentMember.setTelephone(tel);
            }
            Optional<Minister> min = ministerService.getMinister(member.getMinistere());
            if (min.isPresent()) {
                currentMember.setMinistere(min.get());
            }
            String photo = member.getPhoto();
            if (photo != null) {
                String photoName = base64ToImage(member);
                currentMember.setPhoto(photoName);
            }

            memberService.saveMember(currentMember);
            return currentMember;
        } else {
            return null;
        }
    }

    /**
     * Delete - Delete an member
     *
     * @param id - The id of the member to delete
     */
    // @PreAuthorize("hasAuthority('PM_DEL_ME')")
    @DeleteMapping("/delete-member/{id}")
    public void deleteMember(@PathVariable("id") final String id) {
        
        Optional<Member> e = memberService.getMember(id);
        if (e.isPresent()) {
            Member currentMember = e.get();
            // suppression du fichier physique
            deleteFile(currentMember.getPhoto());
        }
        
        memberService.deleteMember(id);
    }

}
