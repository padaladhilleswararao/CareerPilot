package com.careerpilot.controller;
import com.careerpilot.entity.Analysis; import com.careerpilot.repository.AnalysisRepository; import com.careerpilot.service.OpenAiService; import org.springframework.http.*; import org.springframework.web.bind.annotation.*; import java.util.*;
@RestController @RequestMapping("/api/career")
public class CareerController {
 private final OpenAiService ai; private final AnalysisRepository repo;
 public CareerController(OpenAiService a,AnalysisRepository r){ai=a;repo=r;}
 @PostMapping("/analyze") public ResponseEntity<?> analyze(@RequestBody Map<String,Object> body){String resume=String.valueOf(body.getOrDefault("resumeText",""));Long userId=Long.valueOf(String.valueOf(body.getOrDefault("userId","0")));if(resume.isBlank())return ResponseEntity.badRequest().body(Map.of("message","Resume text is required."));String result=ai.generate("Analyze this resume. Return: 1) short profile summary 2) strengths 3) missing skills 4) improvement suggestions 5) ATS-friendly keywords 6) suitable entry-level roles.",resume);Analysis a=new Analysis();a.setUserId(userId);a.setResumeText(resume);a.setResult(result);repo.save(a);return ResponseEntity.ok(Map.of("id",a.getId(),"result",result));}
 @PostMapping("/interview-questions") public Map<String,String> questions(@RequestBody Map<String,String> body){return Map.of("result",ai.generate("Generate 10 tailored interview questions with concise sample answer guidance. Target role: "+body.getOrDefault("role","Software Engineer"),body.getOrDefault("resumeText","No resume supplied.")));}
 @PostMapping("/skills") public Map<String,String> skills(@RequestBody Map<String,String> body){return Map.of("result",ai.generate("Recommend a prioritized skill roadmap for the target role: "+body.getOrDefault("role","Software Engineer")+". Include beginner-to-advanced order and one project idea per major skill.",body.getOrDefault("resumeText","No resume supplied.")));}
 @GetMapping("/history/{userId}") public List<Analysis> history(@PathVariable Long userId){return repo.findByUserIdOrderByCreatedAtDesc(userId);}
}
