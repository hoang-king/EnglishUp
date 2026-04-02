# 🤖 AI Agent Instruction: Generic Android Layer-based Generator (Clean Architecture)

Tài liệu này là bộ khung (template) chuẩn để AI Agent tự động tạo các thành phần của một tính năng mới theo mô hình **Layer-based Clean Architecture**.

---

# 🎯 Mục tiêu

Tạo mã nguồn cho một tính năng mới và đưa trực tiếp vào các lớp (layers) có sẵn của dự án, đảm bảo tính tách biệt giữa Business Logic và Framework.

---

# 📦 Input (Tham số đầu vào)

Khi yêu cầu AI, hãy cung cấp các thông tin:
1. **project_package**: (Ví dụ: `com.example.myapp`)
2. **feature_name**: (Ví dụ: `order`, `user_profile`)
3. **entity_name**: (Ví dụ: `Order`, `User`)
4. **fields**: Danh sách các trường dữ liệu (Ví dụ: `id: String`, `amount: Double`).

---

# 🏗️ 1. Cấu trúc Package chuẩn (Layer-based)

Tất cả các file phải được đặt vào đúng các package layer sau:

```text
{project_package}/
├── core/                     # Các lớp cơ sở (Base Classes)
│   ├── Mapper.kt             # Chuyển đổi DTO <-> Entity
│   └── BaseRepository.kt     # Interface CRUD cơ bản
│
├── entities/                 # Domain Models (POJO/Data Class)
│   └── {EntityName}.kt
│
├── usecases/                 # Business Logic (Single Responsibility)
│   └── Get{EntityName}ListUseCase.kt
│
├── repositories/             # Domain Interfaces
│   └── {EntityName}Repository.kt
│
├── adaptors/                 # Data Layer Implementations
│   ├── mapper/               # {EntityName}Mapper.kt
│   ├── repositories/         # {EntityName}RepositoryImpl.kt
│   └── datasources/          # {EntityName}Dto.kt, {EntityName}LocalDataSource.kt, {EntityName}RemoteDataSource.kt
│
├── view_model/               # Presentation Layer (UI State)
│   └── {EntityName}ViewModel.kt
│
├── containers/               # Dependency Injection (Manual or Hilt)
│   └── {EntityName}Container.kt
│
└── frameworks/
    └── ui/                   # Framework-specific (Compose/XML)
        └── {EntityName}Screen.kt
```

---

# 🧠 2. Quy tắc vàng (Core Mandates)

1. **NO Feature Folders:** Tuyệt đối không tạo thư mục bao quanh tên tính năng. Tất cả file `{EntityName}X.kt` phải nằm trực tiếp trong package layer tương ứng.
2. **Dependency Flow:** 
   - `UI` -> `ViewModel` -> `UseCase` -> `Repository (Interface)`.
   - `Repository (Impl)` -> `DataSource`.
3. **Core Reusability:** Luôn kế thừa từ `core.Mapper` và `core.BaseRepository` nếu đã có.
4. **Naming Convention:** Bắt đầu tên file bằng tên của Feature hoặc Entity (CamelCase).

---

# ⚙️ 3. Quy trình thực hiện (Workflow)

1. **Bước 1 (Domain):** Tạo Entity và Repository Interface.
2. **Bước 2 (Domain):** Tạo các UseCase (mỗi UseCase 1 nhiệm vụ duy nhất).
3. **Bước 3 (Data):** Tạo DTO, Mapper và các DataSource (Local/Remote).
4. **Bước 4 (Data):** Tạo Repository Implementation để điều phối dữ liệu.
5. **Bước 5 (Presentation):** Tạo ViewModel quản lý State và UI Screen.
6. **Bước 6 (DI):** Đăng ký/Khởi tạo các dependencies trong Container.

---

# 💡 4. Prompt mẫu (Dùng để ra lệnh cho AI)

> "Hãy tạo tính năng **{feature_name}** cho dự án **{project_package}**. 
> Sử dụng thực thể **{EntityName}** với các trường: **{fields}**. 
> Áp dụng cấu trúc Layer-based chuẩn trong structure.MD. 
> Đừng tạo thư mục feature con, hãy đưa file vào các package layer có sẵn."

---

# ✅ DONE
AI Agent chỉ cần đọc file này là có thể triển khai Clean Architecture cho bất kỳ dự án Android nào theo phong cách Layer-based.
