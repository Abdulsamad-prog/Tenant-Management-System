Tenant Management System
Members
Collins Surtan - 169232 [PR #1](https://github.com/Abdulsamad-prog/Tenant-Management-System/pull/1) 
Akio Owino - 186508 [PR #2](https://github.com/Abdulsamad-prog/Tenant-Management-System/pull/2) 
Stacie Nganga - 156564 [PR #3](https://github.com/Abdulsamad-prog/Tenant-Management-System/pull/3) 
Mark Mwiti - 189625 [PR #4](https://github.com/Abdulsamad-prog/Tenant-Management-System/pull/4) 
Abdulsamad Abdikadir - 168937 [PR #5](https://github.com/Abdulsamad-prog/Tenant-Management-System/pull/5) 
About the App
The Tenant Management System is an Android application built in Kotlin that helps landlords manage residential properties, track monthly rental income, and organize tenant records in one place. Landlords can create an account and sign in with persistent sessions, view real-time occupancy and total rent (`KSh`) on the Dashboard, and register new tenants along with their assigned house or unit numbers. The tenant directory displays each resident on an individual card with one-tap phone dialing and confirmation-protected removal, ensuring everyday property management stays fast and accurate.

Under the hood, the application follows a clean Model-View-ViewModel (MVVM) architecture that separates the `ui/` layer (Activities, Fragments, `RecyclerView` adapter, and `TenantViewModel`) from the `data/` layer (`TenantRepository`, `SessionManager`, and a Room SQLite database). All tenant records persist locally across screen rotations and full app restarts using `LiveData` and Kotlin Coroutines. To install the app on an Android device, download the signed `v1.0` APK from the **Releases** section on the right side of this repository.
