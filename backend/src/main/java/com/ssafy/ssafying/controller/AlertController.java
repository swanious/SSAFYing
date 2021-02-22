// package com.ssafy.ssafying.controller;

// import java.util.HashMap;
// import java.util.Map;

// import com.ssafy.ssafying.model.dto.AlertDto;
// import com.ssafy.ssafying.model.service.AlertService;
// import com.ssafy.ssafying.model.service.JwtService;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.stereotype.Controller;
// import org.springframework.web.bind.annotation.DeleteMapping;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;

// import io.swagger.annotations.Api;

// @Api("CommentController V1")
// @Controller
// @RequestMapping("/alert")
// public class AlertController {

//     public static final Logger logger = LoggerFactory.getLogger(AlertController.class);

//     @Autowired
//     private AlertService alertService;

//     @Autowired
//     private JwtService jwtService;

//     @GetMapping(value = "/")
//     public ResponseEntity<Map<String, Object>> alertSelectOne(){
//         Map<String, Object> resultMap = new HashMap<>();
//         HttpStatus status = null;

//         try {
//             resultMap.put("alertDtoList", alertService.alertSelectByKeyWord("userId", jwtService.getUserId()));

//             logger.info("알람 조회 성공");
//             resultMap.put("message", "success");
//             status = HttpStatus.ACCEPTED;
//         } catch (Exception e) {
//             logger.info("알람 조회 실패");
//             resultMap.put("message", e.getMessage());
//             status = HttpStatus.INTERNAL_SERVER_ERROR;
//         }

//         return new ResponseEntity<Map<String, Object>>(resultMap, status);
//     }

//     @PostMapping(value = "/")
//     public ResponseEntity<Map<String, Object>> alertInsert(@RequestBody AlertDto alertDto){
//         Map<String, Object> resultMap = new HashMap<>();
//         HttpStatus status = null;

//         try {
//             alertService.alertInsert(alertDto);

//             logger.info("알람 조회 성공");
//             resultMap.put("message", "success");
//             status = HttpStatus.ACCEPTED;
//         } catch (Exception e) {
//             logger.info("알람 조회 실패");
//             resultMap.put("message", e.getMessage());
//             status = HttpStatus.INTERNAL_SERVER_ERROR;
//         }

//         return new ResponseEntity<Map<String, Object>>(resultMap, status);
//     }

//     @DeleteMapping(value = "/{alertId}")
//     public ResponseEntity<Map<String, Object>> alertDelete(@PathVariable("alertId") int alertId){
//         Map<String, Object> resultMap = new HashMap<>();
//         HttpStatus status = null;

//         try {
//             alertService.alertDelete(alertId);

//             logger.info("알람 삭제 성공");
//             resultMap.put("message", "success");
//             status = HttpStatus.ACCEPTED;
//         } catch (Exception e) {
//             logger.info("알람 삭제 실패");
//             resultMap.put("message", e.getMessage());
//             status = HttpStatus.INTERNAL_SERVER_ERROR;
//         }

//         return new ResponseEntity<Map<String, Object>>(resultMap, status);
//     }
// }
