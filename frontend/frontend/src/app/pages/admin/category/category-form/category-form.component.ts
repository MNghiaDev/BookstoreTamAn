import { Component } from '@angular/core';
import { Category, IListCategory } from '../../../../models/category';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastService } from '../../../../core/toast.service';
import { CategoryService } from '../../../../core/category.service';
import { environment } from '../../../../../environments/environment';

@Component({
  selector: 'app-category-form',
  imports: [ FormsModule, ],
  templateUrl: './category-form.component.html',
  styleUrl: './category-form.component.css'
})
export class CategoryFormComponent {

    category : Category = new Category();

    isEditMode : boolean = false;
    selectedFile : File | null = null;

    private apiUrl = environment.apiUrl;
    constructor(private router : Router, private route : ActivatedRoute,
      private toastService : ToastService,
      private categoryService : CategoryService
    ){}
    savecategory(){
       if(this.isEditMode){
      this.categoryService.updateCategory(this.category.id, this.category).subscribe((res : any) =>{
        this.toastService.showToast("Cập nhật thể loại thành công!");
        this.router.navigateByUrl('/admin/categories');
      })
    }else{
      this.categoryService.createCategory(this.category).subscribe((res : any) =>{
      this.toastService.showToast("Thêm mới thể loại thành công!");
      this.router.navigateByUrl("/admin/categories");
      })
    }
    }
    cancel(){
      this.router.navigateByUrl('/admin/categories');
    }

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.isEditMode = true;
      this.categoryService.getCategoryById(+id).subscribe((res:any) => {
        this.category = res.data;
      });
    }
  }
}
