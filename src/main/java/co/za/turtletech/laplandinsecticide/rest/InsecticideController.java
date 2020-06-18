package co.za.turtletech.laplandinsecticide.rest;

import co.za.turtletech.laplandinsecticide.model.BlockTransaction;
import co.za.turtletech.laplandinsecticide.model.BlockTransactionAudit;
import co.za.turtletech.laplandinsecticide.model.Cultivar;
import co.za.turtletech.laplandinsecticide.model.FormattedResponse;
import co.za.turtletech.laplandinsecticide.model.InsecticideDetails;
import co.za.turtletech.laplandinsecticide.model.enums.BlockTransactionEnum;
import co.za.turtletech.laplandinsecticide.rest.impl.InsecticideRepositoryImpl;
import com.google.gson.Gson;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("insecticide")
public class InsecticideController {

    final InsecticideRepositoryImpl insecticideRepository;

    public InsecticideController(InsecticideRepositoryImpl insecticideRepository) {
        this.insecticideRepository = insecticideRepository;
    }

    @ApiOperation(value = "get all farm transactions", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Farm Transactions Received", response = List.class),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @GetMapping(value = "/blockTransaction/{userID}", produces = "application/json")
    public ResponseEntity<?> allFarmTransactions(@PathVariable String userID) {
        return ResponseEntity.status(200).body(new Gson().toJson(insecticideRepository.allFarmTransactions(userID)));
    }

    @ApiOperation(value = "get a block transaction based on block name", response = BlockTransaction.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Block Transaction Received", response = BlockTransaction.class),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 404, message = "Block not found")
    })
    @GetMapping(value = "/blockTransaction/{userID}/{name}", produces = "application/json")
    public ResponseEntity<?> blockTransaction(@PathVariable String userID, @PathVariable String name) {
        BlockTransaction blockTransaction = insecticideRepository.findBlockTransaction(userID, name);
        Gson g = new Gson();
        String block_not_found = g.toJson("Block with name: " + name + " not found");
        if (blockTransaction == null)
            return ResponseEntity.status(404).body(block_not_found);
        else
            return ResponseEntity.status(200).body(blockTransaction);
    }

    @ApiOperation(value = "insert a new block transaction ", response = FormattedResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Block Transaction Added", response = FormattedResponse.class),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @PostMapping(value = "/blockTransaction/{userId}", produces = "application/json")
    public ResponseEntity<?> insertBlockTransaction(@PathVariable String userId,
                                                    @RequestBody BlockTransaction blockTransaction) {
        blockTransaction.setModifiedBy(userId);
        insecticideRepository.insertCurrentBlockTransaction(userId, blockTransaction);

        Gson g = new Gson();
        String message = g.toJson("Block with name: " +
                BlockTransactionEnum.valueOfLabel(blockTransaction.getBlockID()).name() + " created");

        return ResponseEntity.status(201).body(
                new FormattedResponse(blockTransaction.getBlockID(), 201, message));
    }

    @ApiOperation(value = "update an existing block transaction ", response = FormattedResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Block Transaction Added", response = FormattedResponse.class),
            @ApiResponse(code = 404, message = "Block Transaction not found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @PutMapping(value = "/blockTransaction/{userId}", produces = "application/json")
    public ResponseEntity<?> updateBlockTransaction(@PathVariable String userId,
                                                    @RequestBody BlockTransaction blockTransaction) {
        String name = BlockTransactionEnum.valueOfLabel(blockTransaction.getBlockID()).name();
        Gson g = new Gson();
        String message = g.toJson("Block with name: " + name + " created");

        if (name != null) {
            blockTransaction.setModifiedBy(userId);
            if (insecticideRepository.findBlockTransaction(userId, name) != null) {
                insecticideRepository.updateCurrentBlockTransaction(userId, blockTransaction);
                message = g.toJson("Block with name: " + name + " updated");
            } else {
                insecticideRepository.insertCurrentBlockTransaction(userId, blockTransaction);
                message = g.toJson("Block with name: " + name + " created");
            }
        } else {
            message = g.toJson("Block with name: " + name + " not found");
            return ResponseEntity.status(404).body(
                    new FormattedResponse(blockTransaction.getBlockID(), 404, message));
        }

        return ResponseEntity.status(204).body(
                new FormattedResponse(blockTransaction.getBlockID(), 204, message));
    }

    @ApiOperation(value = "get a block transaction audit trail based on block name", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Block Transaction Received", response = List.class),
            @ApiResponse(code = 404, message = "Block not found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @GetMapping(value = "/blockTransactionAudit/{userId}/{name}", produces = "application/json")
    public ResponseEntity<?> blockTransactionAudit(@PathVariable String userId, @PathVariable String name) {
        String blockID = BlockTransactionEnum.valueOf(name).getLabel();

        return ResponseEntity.status(200).body(
                new Gson().toJson(insecticideRepository.blockTransactionHistory(userId, blockID)));

    }

    @ApiOperation(value = "insert a new cultivar", response = FormattedResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Cultivar created", response = FormattedResponse.class),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @PostMapping(value = "/cultivar/{userId}/", produces = "application/json")
    public ResponseEntity<?> insertCultivar(@PathVariable String userId, @RequestBody Cultivar cultivar) {
        cultivar.setModifiedBy(userId);
        Cultivar insertCultivar = insecticideRepository.insertCultivar(userId, cultivar);

        Gson g = new Gson();
        String message = g.toJson("Cultivar with name: " +
                cultivar.getCultivarName() + " created");

        return ResponseEntity.status(201).body(
                new FormattedResponse(insertCultivar.getCultivarID(), 201, message));


    }

    @ApiOperation(value = "update an existing cultivar", response = FormattedResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Cultivar updated", response = FormattedResponse.class),
            @ApiResponse(code = 404, message = "Cultivar not found", response = FormattedResponse.class),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @PutMapping(value = "/cultivar/{userId}/{name}", produces = "application/json")
    public ResponseEntity<?> updateCultivar(@PathVariable String userId, @PathVariable String name,
                                            @RequestBody Cultivar cultivar) {
        cultivar.setModifiedBy(userId);
        int updateCultivar = insecticideRepository.updateCultivar(userId, name, cultivar, false);

        Gson g = new Gson();
        String message;

        if (updateCultivar == 1) {
            message = g.toJson("Cultivar with name: " +
                    cultivar.getCultivarName() + " updated");
            return ResponseEntity.status(204).body(
                    new FormattedResponse(cultivar.getCultivarID(), 204, message));
        } else
            message = g.toJson("Cultivar with name: " +
                    cultivar.getCultivarName() + " not found");

        return ResponseEntity.status(404).body(
                new FormattedResponse(cultivar.getCultivarID(), 404, message));
    }

    @ApiOperation(value = "get an existing cultivar", response = Cultivar.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Cultivar found", response = Cultivar.class),
            @ApiResponse(code = 404, message = "Cultivar not found", response = FormattedResponse.class),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @GetMapping(value = "/cultivar/{userId}/{name}", produces = "application/json")
    public ResponseEntity<?> getCultivar(@PathVariable String userId, @PathVariable String name) {
        Cultivar cultivar = insecticideRepository.findCultivar(userId, name);

        Gson g = new Gson();
        String cultivarNotFound = g.toJson("Cultivar with name: " + name + " not found");
        if (cultivar == null)
            return ResponseEntity.status(404).body(cultivarNotFound);
        else
            return ResponseEntity.status(200).body(cultivar);
    }

    @ApiOperation(value = "delete an existing cultivar", response = FormattedResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Cultivar deleted", response = FormattedResponse.class),
            @ApiResponse(code = 404, message = "Cultivar not found", response = FormattedResponse.class),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @DeleteMapping(value = "/cultivar/{userId}/{name}", produces = "application/json")
    public ResponseEntity<?> deleteCultivar(@PathVariable String userId, @PathVariable String name,
                                            @RequestBody Cultivar cultivar) {
        cultivar.setModifiedBy(userId);
        int updateCultivar = insecticideRepository.updateCultivar(userId, name, cultivar, true);

        Gson g = new Gson();
        String message;

        if (updateCultivar == 1) {
            message = g.toJson("Cultivar with name: " + cultivar.getCultivarName() + " deleted");
            return ResponseEntity.status(204).body(
                    new FormattedResponse(cultivar.getCultivarID(), 204, message));
        } else
            message = g.toJson("Cultivar with name: " + cultivar.getCultivarName() + " not found");

        return ResponseEntity.status(404).body(
                new FormattedResponse(cultivar.getCultivarID(), 201, message));
    }

    @ApiOperation(value = "insert a new insecticide details", response = FormattedResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Insecticide details created", response = FormattedResponse.class),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @PostMapping(value = "/insecticideDetails/{userId}/", produces = "application/json")
    public ResponseEntity<?> insertInsecticideDetails(@PathVariable String userId,
                                                      @RequestBody InsecticideDetails insecticideDetails) {
        insecticideDetails.setModifiedBy(userId);
        InsecticideDetails insertInsecticideDetails = insecticideRepository.insertInsecticideDetails(userId, insecticideDetails);

        Gson g = new Gson();
        String message = g.toJson("Cultivar with name: " + insertInsecticideDetails.getStreetName() + " created");

        return ResponseEntity.status(201).body(
                new FormattedResponse(insertInsecticideDetails.getInsecticideDetailsID(), 201, message));


    }

    @ApiOperation(value = "update an existing insecticide details", response = FormattedResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Insecticide details updated", response = FormattedResponse.class),
            @ApiResponse(code = 404, message = "Insecticide details not found", response = FormattedResponse.class),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @PutMapping(value = "/insecticideDetails/{userId}/{name}", produces = "application/json")
    public ResponseEntity<?> updateInsecticideDetails(@PathVariable String userId, @PathVariable String name,
                                                      @RequestBody InsecticideDetails insecticideDetails) {
        insecticideDetails.setModifiedBy(userId);
        int updateInsecticideDetails = insecticideRepository.updateInsecticideDetails(userId, name, insecticideDetails);

        Gson g = new Gson();
        String message;

        if (updateInsecticideDetails == 1) {
            message = g.toJson("Insecticide with name: " + name + " updated");
            return ResponseEntity.status(204).body(
                    new FormattedResponse(insecticideDetails.getInsecticideDetailsID(), 204, message));
        } else
            message = g.toJson("Insecticide with name: " + name + " not found");

        return ResponseEntity.status(404).body(
                new FormattedResponse(insecticideDetails.getInsecticideDetailsID(), 404, message));
    }

    @ApiOperation(value = "get existing insecticide details", response = Cultivar.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Insecticide details found", response = InsecticideDetails.class),
            @ApiResponse(code = 404, message = "Insecticide details not found", response = FormattedResponse.class),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @GetMapping(value = "/insecticideDetails/{userId}/{name}", produces = "application/json")
    public ResponseEntity<?> getInsecticideDetails(@PathVariable String userId, @PathVariable String name) {
        InsecticideDetails insecticideDetails = insecticideRepository.findInsecticideDetails(userId, name);
        Gson g = new Gson();
        String insecticideNotFound = g.toJson("Insecticide with name: " + name + " not found");
        if (insecticideDetails == null)
            return ResponseEntity.status(404).body(insecticideNotFound);
        else
            return ResponseEntity.status(200).body(insecticideDetails);
    }

    @ApiOperation(value = "delete existing insecticide details", response = FormattedResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Insecticide details deleted", response = FormattedResponse.class),
            @ApiResponse(code = 404, message = "Insecticide details not found", response = FormattedResponse.class),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @DeleteMapping(value = "/insecticideDetails/{userId}/{name}", produces = "application/json")
    public ResponseEntity<?> deleteInsecticideDetails(@PathVariable String userId, @PathVariable String name) {
        int deleteInsecticideDetail;
        String message;
        Gson g = new Gson();

        InsecticideDetails currentInsecticideDetails = insecticideRepository.findInsecticideDetails(userId, name);
        if (currentInsecticideDetails != null) {
            currentInsecticideDetails.setModifiedBy(userId);
            deleteInsecticideDetail = insecticideRepository.deleteInsecticideDetail(userId, currentInsecticideDetails);

            if (deleteInsecticideDetail == 1) {
                message = g.toJson("Insecticide with name: " + name + " deleted");
                return ResponseEntity.status(204).body(
                        new FormattedResponse(currentInsecticideDetails.getInsecticideDetailsID(), 204, message));
            } else
                message = g.toJson("Insecticide with name: " + name + " not deleted");
        } else
            message = g.toJson("Insecticide with name: " + name + " not found");

        return ResponseEntity.status(404).body(
                new FormattedResponse(currentInsecticideDetails.getInsecticideDetailsID(), 404, message));
    }

}
