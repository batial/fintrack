package batial.fintrack.category;

import batial.fintrack.user.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<List<Category>> getAll(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(categoryService.findByUser(user));
    }

    @PostMapping
    public ResponseEntity<Category> create(@RequestBody Category category,
                                           @AuthenticationPrincipal User user) {
        category.setUser(user);
        return ResponseEntity.ok(categoryService.save(category));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}