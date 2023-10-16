package com.example.finalproject.controller;

import com.example.finalproject.dto.MemberDTO;
import com.example.finalproject.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final EmailService emailService;

    @GetMapping("/login")
    public String login(){
        return "/menu/login";
    }

    @PostMapping("/login")
    public ResponseEntity login(@ModelAttribute MemberDTO memberDTO, HttpSession session){
        System.out.println("memberDTO = " + memberDTO);
            session.setAttribute("user", memberDTO.getMemberEmail());
            session.setAttribute("userId", memberDTO.getId());
            return new ResponseEntity<>(memberDTO, HttpStatus.OK);
    }

    @GetMapping("/logout")
    public ResponseEntity logout(HttpSession session, HttpServletResponse response){
        session.removeAttribute("user");
        session.removeAttribute("userId");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/emailConfirm")
    public ResponseEntity emailConfirm(@RequestParam String email) throws Exception {
        String confirm = emailService.sendSimpleMessage(email);
        return new ResponseEntity<>(confirm, HttpStatus.OK);
    }
}
