package services;

import dao.InventaireDAO;
import dto.InventaireDTO;
import entity.Inventaire;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class InventaireService {

    @Inject
    InventaireDAO inventoryDAO;

    public InventaireDTO getInventoryById(Long id) {
        Inventaire inventory = inventoryDAO.findById(id);
        if (inventory == null) {
            return null;
        }
        return new InventaireDTO(inventory.getUserId(), inventory.getName(), inventory.getQuantity());
    }

    public List<InventaireDTO> getInventoryByUserId(Long userId) {
        return inventoryDAO.findByUserId(userId)
                .stream()
                .map(inventory -> new InventaireDTO(inventory.getUserId(), inventory.getName(), inventory.getQuantity()))
                .collect(Collectors.toList());
    }

    @Transactional
    public void addOrUpdateInventory(InventaireDTO inventoryDTO) {
        Inventaire inventory = new Inventaire();
        inventory.setUserId(inventoryDTO.userId());
        inventory.setName(inventoryDTO.resourceName());
        inventory.setQuantity(inventoryDTO.resouceQuantity());

        inventoryDAO.saveInventory(inventory);
    }

    @Transactional
    public void removeInventory(Long id) {
        inventoryDAO.deleteInventory(id);
    }

}
