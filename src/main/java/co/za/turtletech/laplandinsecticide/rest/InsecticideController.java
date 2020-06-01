package co.za.turtletech.laplandinsecticide.rest;

import co.za.turtletech.laplandinsecticide.model.BlockTransaction;
import co.za.turtletech.laplandinsecticide.model.SuccessResponse;
import co.za.turtletech.laplandinsecticide.rest.impl.InsecticideRepositoryImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("insecticide")
public class InsecticideController {

    final InsecticideRepositoryImpl insecticideRepository;

    Logger logger = LoggerFactory.getLogger(InsecticideController.class);

    public InsecticideController(InsecticideRepositoryImpl insecticideRepository) {
        this.insecticideRepository = insecticideRepository;
    }

    @ApiOperation(value = "get all farm transactions", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Farm Transactions Received", response = List.class),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @GetMapping(value = "/blockTransaction/{userID}", produces = "application/json")
    public @ResponseBody
    List<BlockTransaction> allFarmTransactions(@PathVariable String userID) {
        return insecticideRepository.allFarmTransactions(userID);
    }

    @ApiOperation(value = "get a block transaction based on block name", response = BlockTransaction.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Block Transaction Received", response = BlockTransaction.class),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 404, message = "Block not found")
    })
    @GetMapping(value = "/blockTransaction/{userID}/{name}", produces = "application/json")
    public @ResponseBody
    BlockTransaction blockTransaction(@PathVariable String userID,
                                      @PathVariable String name) {
        return insecticideRepository.findBlockTransaction(userID, name);
    }

    @ApiOperation(value = "insert a new block transaction ", response = SuccessResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Block Transaction Added", response = SuccessResponse.class),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @PostMapping(value = "/blockTransaction/{userId}", produces = "application/json")
    public @ResponseBody
    SuccessResponse blockTransaction(@PathVariable String userId,
                                     @RequestBody BlockTransaction blockTransaction) {
        blockTransaction.setModifiedBy(userId);
        insecticideRepository.insertCurrentBlockTransaction(userId, blockTransaction);
        return new SuccessResponse(blockTransaction.getBlockID(), 201);
    }
}
