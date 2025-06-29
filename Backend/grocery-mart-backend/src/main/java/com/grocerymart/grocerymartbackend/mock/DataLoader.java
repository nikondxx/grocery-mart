package com.grocerymart.grocerymartbackend.mock;

import com.grocerymart.grocerymartbackend.model.DeliveryOption;
import com.grocerymart.grocerymartbackend.model.Product;
import com.grocerymart.grocerymartbackend.repository.DeliveryOptionRepository;
import com.grocerymart.grocerymartbackend.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class DataLoader {

    @Bean
    public CommandLineRunner loadData(ProductRepository productRepository, DeliveryOptionRepository deliveryOptionRepository) {
        return args -> {
            if (productRepository.count() == 0) {
                List<Product> sampleProducts = List.of(
                        new Product(null, "Fresh Bananas", 2.99,
                                "https://images.unsplash.com/photo-1571771894821-ce9b6c11b08e?w=400&h=300&fit=crop",
                                "Fruits", true, "Fresh, ripe bananas perfect for snacking or baking"),
                        new Product(null, "Organic Apples", 4.99,
                                "https://images.unsplash.com/photo-1560806887-1e4cd0b6cbd6?w=400&h=300&fit=crop",
                                "Fruits", true, "Crisp organic apples, perfect for healthy snacking"),
                        new Product(null, "Fresh Milk", 3.49,
                                "https://images.unsplash.com/photo-1563636619-e9143da7973b?w=400&h=300&fit=crop",
                                "Dairy", true, "Fresh whole milk from local farms"),
                        new Product(null, "Whole Grain Bread", 2.79,
                                "https://images.unsplash.com/photo-1509440159596-0249088772ff?w=400&h=300&fit=crop",
                                "Bakery", true, "Freshly baked whole grain bread"),
                        new Product(null, "Free-Range Eggs", 5.99,
                                "https://images.unsplash.com/photo-1582722872445-44dc5f7e3c8f?w=400&h=300&fit=crop",
                                "Dairy", true, "Farm-fresh free-range eggs"),
                        new Product(null, "Fresh Tomatoes", 3.99,
                                "https://images.unsplash.com/photo-1546094096-0df4bcaaa337?w=400&h=300&fit=crop",
                                "Vegetables", true, "Juicy, ripe tomatoes perfect for salads"),
                        new Product(null, "Organic Carrots", 2.49,
                                "https://images.unsplash.com/photo-1445282768818-728615cc910a?w=400&h=300&fit=crop",
                                "Vegetables", false, "Fresh organic carrots, perfect for cooking"),
                        new Product(null, "Greek Yogurt", 4.49,
                                "https://images.unsplash.com/photo-1571212515416-bf4df43e5b2a?w=400&h=300&fit=crop",
                                "Dairy", true, "Creamy Greek yogurt, high in protein"),
                        new Product(null, "Fresh Salmon", 12.99,
                                "https://images.unsplash.com/photo-1574781330855-d0db2706b3d0?w=400&h=300&fit=crop",
                                "Seafood", true, "Fresh Atlantic salmon fillet"),
                        new Product(null, "Avocados", 1.99,
                                "https://images.unsplash.com/photo-1560272564-c83b66b1ad12?w=400&h=300&fit=crop",
                                "Fruits", true, "Perfectly ripe avocados"),
                        new Product(null, "Chicken Breast", 8.99,
                                "https://images.unsplash.com/photo-1604503468506-a8da13d82791?w=400&h=300&fit=crop",
                                "Meat", true, "Fresh boneless chicken breast"),
                        new Product(null, "Mixed Berries", 6.99,
                                "https://images.unsplash.com/photo-1506905925346-21bda4d32df4?w=400&h=300&fit=crop",
                                "Fruits", true, "Fresh mixed berries - strawberries, blueberries, raspberries")
                );

                productRepository.saveAll(sampleProducts);
            }
            // ✅ Insert delivery options
            if (deliveryOptionRepository.count() == 0) {
                List<DeliveryOption> deliveryOptions = List.of(
                        new DeliveryOption(null, "Standard Delivery", 5.99, "2-3 business days"),
                        new DeliveryOption(null, "Express Delivery", 12.99, "Next business day"),
                        new DeliveryOption(null, "Same Day Delivery", 19.99, "Within 4-6 hours")
                );
                deliveryOptionRepository.saveAll(deliveryOptions);
            }


        };
    }
}
