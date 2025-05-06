package com.AssetManagementSystem.Manager.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
public class AssetsController {


    // @GetMapping("/assets-name")
    // public ResponseEntity<String> AssetNameDB(@RequestParam("n") String
    // AssetName, @RequestParam("c") int cost) {
    // return ResponseEntity.ok(assetsService.printAU(AssetName, cost));
    // }

    // @GetMapping("/assets-id")
    // public int getAssetId() {
    // return 201;
    // }

    // // Use search assets name from the list
    // @GetMapping("/get-asset")
    // public ResponseEntity<String> searchAssets(@RequestParam("n") String
    // assetName, @RequestParam("c") int cost) {
    // List<Assets> matches = assetsService.searchAssets(assetName, cost);
    // StringBuilder sb = new StringBuilder();
    // if (matches != null) {
    // for (Assets m : matches) {
    // sb.append(String.format("Asset name is : %s The cost is : %d%n", m.getName(),
    // m.getCost()));
    // }
    // }
    // return ResponseEntity.ok(sb.toString());
    // }

    // //////////////////////////////////////////////////////////////////////////////
    // @PostMapping("/save")
    // public ResponseEntity<Assets> saveAssetName(@RequestBody Assets asset) {
    // return ResponseEntity.ok(asset);
    // }

}