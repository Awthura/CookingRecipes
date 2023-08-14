package ain.se.recipe.models;

import java.io.ByteArrayOutputStream;

import ain.se.recipe.Repository.RecipeRepository;
import com.itextpdf.io.image.ImageDataFactory;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import java.io.ByteArrayOutputStream;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;

@Service
public class RecipePdfGenerator {
    private ByteArrayOutputStream pdfOutputStream;

    private final RecipeRepository recipeRepository;

    @Autowired
    public RecipePdfGenerator(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public void generateRecipePdf(ObjectId recipeId) throws Exception {
        Optional<Recipes> optionalRecipe = recipeRepository.findById(recipeId);
        if (optionalRecipe.isPresent()) {
            Recipes recipe = optionalRecipe.get();

            // Create a new ByteArrayOutputStream
            pdfOutputStream = new ByteArrayOutputStream();

            // Create a new PDF document
            PdfWriter pdfWriter = new PdfWriter(pdfOutputStream);
            PdfDocument pdf = new PdfDocument(pdfWriter);

            // Create a new PDF document
            Document document = new Document(pdf);

            // Set font
            PdfFont font = PdfFontFactory.createFont();

            // Add recipe details to the PDF
            document.add(new Paragraph("Recipe Name: " + recipe.getRecipe_name()).setFont(font));
            document.add(new Paragraph("Category: " + recipe.getCategory()).setFont(font));
            document.add(new Paragraph("Prep Time: " + recipe.getPrep_time()).setFont(font));
            document.add(new Paragraph("Cook Time: " + recipe.getCook_time()).setFont(font));
            document.add(new Paragraph("Ingredients: " + recipe.getIngredients()).setFont(font));
            document.add(new Paragraph("Cooking steps: " + recipe.getDirections()).setFont(font));

            // Add other recipe details as needed

            // Add recipe image to the PDF
            Image image = new Image(ImageDataFactory.create(recipe.getImg_src()));
            image.setHorizontalAlignment(HorizontalAlignment.CENTER);
            document.add(image);

            // Close the document
            document.close();
            System.out.println("PDF generated successfully.");
        } else {
            System.out.println("Recipe not found.");
        }
    }

    public byte[] getPdfBytes() {
        if (pdfOutputStream != null) {
            return pdfOutputStream.toByteArray();
        } else {
            return null;
        }
    }

    // Other methods and dependencies
}
